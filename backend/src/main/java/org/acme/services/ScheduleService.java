package org.acme.services;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import org.acme.entities.SatelliteInfoHistoryEntity;
import org.acme.entities.SunExposuresEntity;
import org.acme.exceptions.CustomException;
import org.acme.models.wheretheissat.WhereTheIssAtSatelliteInfo;
import org.acme.models.wheretheissat.WhereTheIssAtTleData;
import org.acme.services.wheretheissat.WhereTheIssAtService;

import io.quarkus.logging.Log;
import io.quarkus.panache.common.Parameters;
import io.quarkus.scheduler.Scheduled;

@ApplicationScoped
public class ScheduleService {

    @Inject
    WhereTheIssAtService whereTheIssAtService;

    @Scheduled(every = "20s")
    public void updateTleData() {
        try {
            WhereTheIssAtTleData tleData = this.whereTheIssAtService.GetTleDataById(25544);
            TLEService.getInstance().updateTleData(tleData.getLine1(),
                    tleData.getLine2());
        } catch (Exception ex) {
            Log.error("Could not refresh TLE data, cause: " + ex.getMessage());
        }
    }

    String lastVisilibityInserted;
    String daylight = "daylight";
    SunExposuresEntity lastSunExposuresInstance;

    @Scheduled(every = "60s")
    @Transactional
    public void getLast10PositionOfIss() {
        LocalDateTime now = LocalDateTime.now();
        List<Long> last10TimeStamps = new ArrayList<Long>();
        for (int i = 0; i < 6; i++) {
            last10TimeStamps.add(Timestamp.valueOf(now).getTime() / 1000);
            now = now.minusSeconds(10);
        }

        String timestampStringList = last10TimeStamps.stream()
                .map(n -> String.valueOf(n))
                .collect(Collectors.joining(","));

        try {
            List<WhereTheIssAtSatelliteInfo> last10Positions = whereTheIssAtService
                    .GetSatellitePositionByIdAndTimestamps(25544, timestampStringList);

            Collections.sort(last10Positions);

            List<SatelliteInfoHistoryEntity> last10PositionsHistory = new ArrayList<SatelliteInfoHistoryEntity>();
            for (WhereTheIssAtSatelliteInfo info : last10Positions) {
                if (lastVisilibityInserted == null) {
                    lastVisilibityInserted = info.getVisibility();
                }

                if (!lastVisilibityInserted.equals(daylight) && info.getVisibility().equals(daylight)) {
                    SunExposuresEntity newSunExposure = new SunExposuresEntity(
                            info.getTimestamp(),
                            null,
                            info.getLatitude(),
                            info.getLongitude());
                    newSunExposure.persist();
                    Log.info("Created new sun exposure, starting at" + newSunExposure.getStartTimestamp());
                    lastVisilibityInserted = daylight;
                    lastSunExposuresInstance = newSunExposure;
                }

                if (lastVisilibityInserted.equals(daylight) && !info.getVisibility().equals(daylight)) {
                    if (lastSunExposuresInstance == null) {
                        lastSunExposuresInstance = SunExposuresEntity
                                .find("select sunExp from sun_exposures sunExp where sunExp.endTimestamp is null")
                                .firstResult();
                    }

                    if (lastSunExposuresInstance != null) {
                        lastSunExposuresInstance.setEndTimestamp(info.getTimestamp());
                        SunExposuresEntity.update("UPDATE FROM sun_exposures SET endTimestamp = :endTimestamp where startTimestamp = :startTimestamp", Parameters.with("endTimestamp",lastSunExposuresInstance.getEndTimestamp()).and("startTimestamp",lastSunExposuresInstance.getStartTimestamp()));
                        Log.info("Closed sun exposure started at" + lastSunExposuresInstance.getStartTimestamp()
                                + ", ended at " + lastSunExposuresInstance.getEndTimestamp());
                        lastSunExposuresInstance = null;
                        lastVisilibityInserted = "";
                    }
                }

                last10PositionsHistory.add(new SatelliteInfoHistoryEntity(
                        info.getTimestamp(),
                        info.getLatitude(),
                        info.getLongitude(),
                        info.getVisibility()));
            }

            last10Positions.removeIf(p -> !p.getVisibility().equals(daylight));

            if (!last10Positions.isEmpty()) {
                SatelliteInfoHistoryEntity.persist(last10PositionsHistory);

                Log.info("Inserted new SatelliteInfoHistory for " + last10TimeStamps.toString());
            } else {
                Log.info("All new positions were eclipsed");
            }

        } catch (CustomException ex) {
            Log.error(String.format("Could not load SatelliteInfo for %s, cause: %s", timestampStringList,
                    ex.getMessage()));
        }
    }
}
