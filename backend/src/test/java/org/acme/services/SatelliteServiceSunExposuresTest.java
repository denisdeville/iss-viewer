package org.acme.services;

import java.math.BigInteger;
import java.util.List;

import javax.inject.Inject;
import javax.transaction.Transactional;

import org.acme.dto.SunExposuresDTO;
import org.acme.dto.SunExposuresPaginationDTO;
import org.acme.entities.SatelliteInfoHistoryEntity;
import org.acme.entities.SunExposuresEntity;
import org.acme.exceptions.CustomException;
import org.acme.repositories.SatelliteInfoHistoryRepository;
import org.acme.repositories.SunExposuresRepository;
import org.acme.services.wheretheissat.WhereTheIssAtService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
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
            List<SunExposuresDTO> sunExposures = satelliteService.getSatelliteSunExposures();

            int expectedSunExposuresListSize = 3;
            int actualSunExposuresListSize = sunExposures.size();

            Assertions.assertEquals(expectedSunExposuresListSize, actualSunExposuresListSize);
        } catch (CustomException e) {
            Log.error(e);
        }
    }

    @Test
    void GetSatelliteSunExposuresReturnsSunExposureWithSatelliteInfoHistoryCorrectlySet() {
        try {
            List<SunExposuresDTO> sunExposures = satelliteService.getSatelliteSunExposures();

            for (SunExposuresDTO sun : sunExposures) {
                Log.info(sun.getStartTimestamp());
            }

            SunExposuresDTO sunExposuresStartingAt2000 = sunExposures.stream()
                    .filter(s -> s.getStartTimestamp().equals(BigInteger.valueOf(2000))).findFirst().orElse(null);

            SunExposuresDTO sunExposuresStartingAt7000 = sunExposures.stream()
                    .filter(s -> s.getStartTimestamp().equals(BigInteger.valueOf(7000))).findFirst().orElse(null);

            SunExposuresDTO sunExposuresStartingAt11000 = sunExposures.stream()
                    .filter(s -> s.getStartTimestamp().equals(BigInteger.valueOf(11000))).findFirst().orElse(null);


            int actualSatelliteInfoInSunExposureStartingAt2000 = sunExposuresStartingAt2000.getSatelliteInfo().size();
            int expectedSatelliteInfoInSunExposureStartingAt2000 = 3;

            
            int actualSatelliteInfoInSunExposureStartingAt7000 = sunExposuresStartingAt7000.getSatelliteInfo().size();
            int expectedSatelliteInfoInSunExposureStartingAt7000 = 1;


            int actualSatelliteInfoInSunExposureStartingAt11000 = sunExposuresStartingAt11000.getSatelliteInfo().size();
            int expectedSatelliteInfoInSunExposureStartingAt11000 = 0;


                    
            Assertions.assertEquals(actualSatelliteInfoInSunExposureStartingAt2000, expectedSatelliteInfoInSunExposureStartingAt2000);
            Assertions.assertEquals(actualSatelliteInfoInSunExposureStartingAt7000, expectedSatelliteInfoInSunExposureStartingAt7000);
            Assertions.assertEquals(actualSatelliteInfoInSunExposureStartingAt11000, expectedSatelliteInfoInSunExposureStartingAt11000);
        } catch (CustomException e) {
            // TODO Auto-generated catch block
            Log.error(e);
        }
    }

    @Test
    void GetSatelliteSunExposuresPaginationReturnsPages() {
        try {

            // Variables 
            // Represent the current page number requestd
            int pageNumber = 0;
            // Represent the size of the page request
            int requestPageSize = 2;
            // Represented the total amount of element present in database (inserted in setup() method)
            int totalNumberOfElement = 3;



            // First page with size of 2 should return 2 elements
            SunExposuresPaginationDTO sunExposures = satelliteService.getSatelliteSunExposuresPagination(0, requestPageSize);

            int expectedPageSize = requestPageSize;
            int actualNumberOfElementInTheList = sunExposures.getSunExposures().size();

            int expectedPageCount = 2;
            int actualPageCount = sunExposures.getPageCount();

            Assertions.assertEquals(expectedPageSize, actualNumberOfElementInTheList);
            Assertions.assertEquals(expectedPageCount, actualPageCount);




            // Next page should only return 1 element
            pageNumber = 1;
            sunExposures = satelliteService.getSatelliteSunExposuresPagination(pageNumber, requestPageSize);

            expectedPageSize = (totalNumberOfElement - (requestPageSize * pageNumber));
            actualNumberOfElementInTheList = sunExposures.getSunExposures().size();

            expectedPageCount = 2;
            actualPageCount = sunExposures.getPageCount();

            Assertions.assertEquals(expectedPageSize, actualNumberOfElementInTheList);
            Assertions.assertEquals(expectedPageCount, actualPageCount);




            // An empty page should ne produce any errors
            // Page number 10 does not exist as we request page of 2 elements and we only have 3 elements in DB
            pageNumber = 10;
            sunExposures = satelliteService.getSatelliteSunExposuresPagination(pageNumber, requestPageSize);

            expectedPageSize = 0;
            actualNumberOfElementInTheList = sunExposures.getSunExposures().size();

            Assertions.assertEquals(expectedPageSize, actualNumberOfElementInTheList);




            // A page requesting more elements that exist in DB should not produce any errors
            pageNumber = 0;
            requestPageSize = 100;
            sunExposures = satelliteService.getSatelliteSunExposuresPagination(pageNumber, requestPageSize);

            expectedPageSize = 3;
            actualNumberOfElementInTheList = sunExposures.getSunExposures().size();

            expectedPageCount = 1;
            actualPageCount = sunExposures.getPageCount();

            Assertions.assertEquals(expectedPageSize, actualNumberOfElementInTheList);
            Assertions.assertEquals(expectedPageCount, actualPageCount);

        } catch (CustomException e) {
            // TODO Auto-generated catch block
            Log.error(e);
        }
    }
}
