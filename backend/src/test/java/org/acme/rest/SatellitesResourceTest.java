package org.acme.rest;

import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.given;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
public class SatellitesResourceTest {
    @Test
    void testBadEndpointReturnsProperError() {
        given()
            .when().get("iss/position/current")
            .then()
                .statusCode(404);
    }

    @Test
    void issPositionEndpointReturnProperStatusCode() {
        given()
            .when().get("iss/position")
            .then()
                .statusCode(200);
    }

    @Test
    void SunEndpointReturnsProperStatusCode() {
        given()
            .when().get("iss/sun")
            .then()
                .statusCode(200);
    }
}
