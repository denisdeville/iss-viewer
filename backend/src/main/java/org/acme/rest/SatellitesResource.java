package org.acme.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;

import org.acme.models.dto.SatelliteModel;
import org.acme.services.SatelliteService;

import java.util.List;

import javax.inject.Inject;

@Path("/iss")
public class SatellitesResource {

    @Inject
    SatelliteService satelliteService;

    @GET
    @Path("/position/{id}")
    public SatelliteModel GetSatellitePosition(@PathParam("id") long id) {
        return satelliteService.GetSatelliteInformationById(id);
    }

    @GET
    @Path("/sun/{id}")
    public List<SatelliteModel> GetSatelliteSunExposion(@PathParam("id") long id, @QueryParam("timestamps") String timestamps) {
        return satelliteService.GetSatelliteSunExposionById(id, timestamps);
    }
    
    @GET
    @Path("/position/mock/{id}")
    public SatelliteModel GetMock(@PathParam("id") long id) {
        return satelliteService.GetSatelliteInformationByIdMock(id);
    }
}