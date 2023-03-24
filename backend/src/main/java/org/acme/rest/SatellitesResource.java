package org.acme.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.acme.dto.SatelliteInfoDTO;
import org.acme.dto.SunExposuresDTO;
import org.acme.dto.SunExposuresPaginationDTO;
import org.acme.entities.SunExposuresEntity;
import org.acme.exceptions.CustomException;
import org.acme.models.dto.CustomExceptionDTO;
import org.acme.models.dto.IssCoordinates;
import org.acme.models.wheretheissat.WhereTheIssAtSatelliteInfo;
import org.acme.models.wheretheissat.WhereTheIssAtTleData;
import org.acme.services.SatelliteService;
import org.acme.services.wheretheissat.WhereTheIssAtService;

import io.quarkus.logging.Log;

import java.util.List;

import javax.inject.Inject;

@Path("/iss")
public class SatellitesResource {

    @Inject
    SatelliteService satelliteService;
    
    @Inject
    WhereTheIssAtService whereTheIssAtService;

    @GET
    @Path("/position")
    public Response GetSatelliteCurrentPosition() {
        try {
            IssCoordinates coordinates = satelliteService.GetSatelliteCurrentPosition();
            return Response.ok().entity(coordinates).build();
        } catch(CustomException exception) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                  .type(MediaType.APPLICATION_JSON)
                  .entity(new CustomExceptionDTO(exception))
                  .build();
        }
        
    }

    @GET
    @Path("/sun")
    public Response GetSatelliteSunExposures() throws CustomException {
        try {
            List<SunExposuresDTO> model = satelliteService.GetSatelliteSunExposures();
            return Response.ok().entity(model).build();
        } catch(CustomException exception) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                  .type(MediaType.APPLICATION_JSON)
                  .entity(new CustomExceptionDTO(exception))
                  .build();
        }
    }

    @GET
    @Path("/sun/pagination")
    public Response GetSatelliteSunExposuresPagination(@QueryParam("pageNumber") int pageNumber, @QueryParam("pageSize") int pageSize) throws CustomException {
        try {
            SunExposuresPaginationDTO model = satelliteService.GetSatelliteSunExposuresPagination(pageNumber, pageSize);
            return Response.ok().entity(model).build();
        } catch(CustomException exception) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                  .type(MediaType.APPLICATION_JSON)
                  .entity(new CustomExceptionDTO(exception))
                  .build();
        }
    }

}