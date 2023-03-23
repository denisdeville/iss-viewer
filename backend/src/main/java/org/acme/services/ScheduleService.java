package org.acme.services;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.acme.models.wheretheissat.WhereTheIssAtTleData;
import org.acme.services.wheretheissat.WhereTheIssAtService;

import io.quarkus.logging.Log;
import io.quarkus.scheduler.Scheduled;

@ApplicationScoped
public class ScheduleService {

    @Inject
    WhereTheIssAtService whereTheIssAtService;

    @Scheduled(every = "20s")
    public void updateTleData() {
        try {
            Log.info("Refreshing TLE data ...");
            WhereTheIssAtTleData tleData = this.whereTheIssAtService.GetTleDataById(25544);
            TLEService.getInstance().updateTleData(tleData.getLine1(), tleData.getLine2());
            Log.info("TLE data refreshed" + tleData.toString());
        } catch (Exception ex) {
            Log.error("Could not refresh TLE data, cause: " + ex.getMessage());
        }
    }
}
