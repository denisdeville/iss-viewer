package org.acme.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import org.acme.models.dto.SatelliteModel;
import org.acme.services.SatelliteService;

import javax.inject.Inject;

@Path("/iss")
public class SatellitesResource {

    @Inject
    SatelliteService satelliteService;

    @GET
    @Path("/position/{id}")
    public SatelliteModel GetSatellitePosition(@PathParam("id") long id) {
        return satelliteService.GetSatelliteById(id);
    }
    
    @GET
    @Path("/position/mock/{id}")
    public SatelliteModel GetMock(@PathParam("id") long id) {
        return satelliteService.GetMock(id);
    }
}