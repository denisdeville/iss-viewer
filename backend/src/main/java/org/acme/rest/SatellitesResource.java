package org.acme.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.acme.dto.SatelliteInfoDTO;
import org.acme.exceptions.CustomException;
import org.acme.models.dto.CustomExceptionDTO;
import org.acme.models.dto.SatelliteModel;
import org.acme.services.SatelliteService;
import org.acme.services.TLEService;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

import javax.inject.Inject;

@Path("/iss")
public class SatellitesResource {

    @Inject
    SatelliteService satelliteService;

    @GET
    @Path("/position/{id}")
    public Response GetSatellitePosition(@PathParam("id") long id) {
        try {
            SatelliteModel model = satelliteService.GetSatelliteInformationById(id);
            return Response.ok().entity(model).build();
        } catch(CustomException exception) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                  .type(MediaType.APPLICATION_JSON)
                  .entity(new CustomExceptionDTO(exception))
                  .build();
        }
        
    }

    @GET
    @Path("/position/{id}/TLE")
    public Response GetSatellitePositionFromTLE(@PathParam("id") long id) {
        double[] coordinates = TLEService.getInstance().getLatitudeLongitude(LocalDateTime.now(ZoneOffset.UTC));
        return Response.ok().entity(coordinates).build();  
    }

    @GET
    @Path("/position/timestamp/{timestamp}")
    public Response GetSatellitePositionFromTLE(@PathParam("timestamp") int timestamp) throws CustomException {
        SatelliteInfoDTO model = satelliteService.GetSatelliteInformationByTimestamp(timestamp); 
        return Response.ok().entity(model).build();
    }

    @GET
    @Path("/sun/{id}")
    public Response GetSatelliteSunExposion(@PathParam("id") long id, @QueryParam("timestamps") String timestamps) throws CustomException {
        try {
            List<SatelliteModel> model = satelliteService.GetSatelliteSunExposionById(id, timestamps);
            return Response.ok().entity(model).build();
        } catch(CustomException exception) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                  .type(MediaType.APPLICATION_JSON)
                  .entity(new CustomExceptionDTO(exception))
                  .build();
        }
    }

}