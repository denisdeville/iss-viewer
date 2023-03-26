package org.acme.services;

import javax.inject.Inject;
import javax.transaction.Transactional;

import org.acme.dto.IssCoordinates;
import org.acme.exceptions.CustomException;
import org.acme.models.wheretheissat.WhereTheIssAtSatelliteInfo;
import org.acme.models.wheretheissat.WhereTheIssAtTleData;
import org.acme.repositories.SunExposuresRepository;
import org.acme.services.wheretheissat.WhereTheIssAtService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.quarkus.logging.Log;
import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
@Transactional
public class SatelliteServiceCurrentPositionTest {

    @Inject
    WhereTheIssAtService whereTheIssAtService;

    @Inject
    SatelliteService satelliteService;

    @Inject
    SunExposuresRepository sunExposuresRepository;

    private double deltaForCoordinatesComparison = 0.02;

    @BeforeEach
    public void setup() {
        try {
            WhereTheIssAtTleData tleData = this.whereTheIssAtService.GetIssTleData();
            TLEService.getInstance().updateTleData(tleData.getLine1(),
                    tleData.getLine2());
        } catch (CustomException ex) {
            Log.error(ex);
        }
    }

    @Test
    void testGetSatelliteCurrentPositionReturnsTheCurrentPositionOfTheIss() {
        try {
           IssCoordinates actualCoordinates = satelliteService.getSatelliteCurrentPosition();

           WhereTheIssAtSatelliteInfo whereTheIssSatelliteInfo = whereTheIssAtService.GetIssCurrentPosition();

           Assertions.assertEquals(actualCoordinates.getLatitude(), whereTheIssSatelliteInfo.getLatitude(), deltaForCoordinatesComparison);
           Assertions.assertEquals(actualCoordinates.getLongitude(), whereTheIssSatelliteInfo.getLongitude(), deltaForCoordinatesComparison);         
        } catch (CustomException ex) {
            Log.error(ex);
        }

    }
}
