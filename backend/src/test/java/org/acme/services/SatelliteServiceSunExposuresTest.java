package org.acme.services;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

import javax.inject.Inject;
import javax.transaction.Transactional;

import org.acme.dto.SunExposuresDTO;
import org.acme.entities.SatelliteInfoHistoryEntity;
import org.acme.entities.SunExposuresEntity;
import org.acme.exceptions.CustomException;
import org.acme.repositories.SatelliteInfoHistoryRepository;
import org.acme.repositories.SunExposuresRepository;
import org.acme.services.wheretheissat.WhereTheIssAtService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.quarkus.logging.Log;
import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
@Transactional
public class SatelliteServiceSunExposuresTest {

    @Inject
    WhereTheIssAtService whereTheIssAtService;

    @Inject
    SatelliteService satelliteService;

    @Inject
    SunExposuresRepository sunExposuresRepository;

    @Inject
    SatelliteInfoHistoryRepository satelliteInfoHistoryRepository;

    private double fakeLatitude = 40;
    private double fakeLongitude = 40;
    private String daylight = "daylight";

    SunExposuresEntity test1;
    SunExposuresEntity test2;
    SunExposuresEntity test3;

    SatelliteInfoHistoryEntity info1;
    SatelliteInfoHistoryEntity info2;
    SatelliteInfoHistoryEntity info3;
    SatelliteInfoHistoryEntity info4;

    // Would have been better with @BeforeAll but couldnt make it work with my repository injection as the method has to be static
    @BeforeEach
    public void setup() {

        // Inserting fake SunExposures in the database
        test1 = new SunExposuresEntity(
                BigInteger.valueOf(2000),
                BigInteger.valueOf(5000),
                fakeLatitude,
                fakeLatitude);

        test2 = new SunExposuresEntity(
                BigInteger.valueOf(7000),
                BigInteger.valueOf(8000),
                fakeLatitude,
                fakeLatitude);

        test3 = new SunExposuresEntity(
                BigInteger.valueOf(11000),
                BigInteger.valueOf(13000),
                fakeLatitude,
                fakeLatitude);

        sunExposuresRepository.save(test1);
        sunExposuresRepository.save(test2);
        sunExposuresRepository.save(test3);

        // Inserting fake SatelliteInfoHistory inside above SunExposures to the database
        info1 = new SatelliteInfoHistoryEntity(
                BigInteger.valueOf(2500),
                fakeLatitude,
                fakeLongitude,
                daylight);

        info2 = new SatelliteInfoHistoryEntity(
                BigInteger.valueOf(2800),
                fakeLatitude,
                fakeLongitude,
                daylight);

        info3 = new SatelliteInfoHistoryEntity(
                BigInteger.valueOf(3500),
                fakeLatitude,
                fakeLongitude,
                daylight);

        info4 = new SatelliteInfoHistoryEntity(
                BigInteger.valueOf(7100),
                fakeLatitude,
                fakeLongitude,
                daylight);

        satelliteInfoHistoryRepository.save(info1);
        satelliteInfoHistoryRepository.save(info2);
        satelliteInfoHistoryRepository.save(info3);
        satelliteInfoHistoryRepository.save(info4);
    }

    // Would have been better with @AfterAll but couldnt make it work with my repository injection as the method has to be static
    @AfterEach
    public void clearDatabase() {
        sunExposuresRepository.deleteAll();
        satelliteInfoHistoryRepository.deleteAll();
    }

    @Test
    void GetSatelliteSunExposuresReturnsAllTheSunExposures() {
        try {
            List<SunExposuresDTO> sunExposures = satelliteService.GetSatelliteSunExposures();

            Assertions.assertEquals(sunExposures.size(), 3);
        } catch (CustomException e) {
            // TODO Auto-generated catch block
            Log.error(e);
        }
    }

    @Test
    void GetSatelliteSunExposuresReturnsSunExposureWithSatelliteInfoHistoryCorrectlySet() {
        try {
            List<SunExposuresDTO> sunExposures = satelliteService.GetSatelliteSunExposures();

            for (SunExposuresDTO sun : sunExposures) {
                Log.info(sun.getStartTimestamp());
            }

            SunExposuresDTO sunExposuresStartingAt2000 = sunExposures.stream()
                    .filter(s -> s.getStartTimestamp().equals(BigInteger.valueOf(2000))).findFirst().orElse(null);

            SunExposuresDTO sunExposuresStartingAt7000 = sunExposures.stream()
                    .filter(s -> s.getStartTimestamp().equals(BigInteger.valueOf(7000))).findFirst().orElse(null);

            SunExposuresDTO sunExposuresStartingAt11000 = sunExposures.stream()
                    .filter(s -> s.getStartTimestamp().equals(BigInteger.valueOf(11000))).findFirst().orElse(null);

            Assertions.assertEquals(sunExposuresStartingAt2000.getSatelliteInfo().size(), 3);
            Assertions.assertEquals(sunExposuresStartingAt7000.getSatelliteInfo().size(), 1);
            Assertions.assertEquals(sunExposuresStartingAt11000.getSatelliteInfo().size(), 0);
        } catch (CustomException e) {
            // TODO Auto-generated catch block
            Log.error(e);
        }
    }
}
