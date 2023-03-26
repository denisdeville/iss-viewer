package org.acme.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.acme.dto.IssCoordinates;
import org.acme.dto.SunExposuresDTO;
import org.acme.dto.SunExposuresPaginationDTO;
import org.acme.exceptions.CustomException;
import org.acme.models.CustomExceptionDTO;
import org.acme.services.SatelliteService;

import java.util.List;

import javax.inject.Inject;

/**
* The SatellitesResource class exposes endpoints to retrieve:
    - current position of the ISS
    - sun exposures history of the ISS
*/
@Path("/iss")
public class SatellitesResource {

    @Inject
    SatelliteService satelliteService;

    /**
     * This method returns the current position of the ISS
     * @return IssCoordinates The return an IssCoordinates instance of the current position of the ISS
     */
    @GET
    @Path("/position")
    public Response GetSatelliteCurrentPosition() {
        try {
            IssCoordinates coordinates = satelliteService.getSatelliteCurrentPosition();
            return Response.ok().entity(coordinates).build();
        } catch(CustomException exception) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                  .type(MediaType.APPLICATION_JSON)
                  .entity(new CustomExceptionDTO(exception))
                  .build();
        }
        
    }

     /**
     * This method returns an object representing all the sun exposures from a given startTimestamp
     * to a given endTimestamp.
     * @return SunExposuresDTO 
     */
    @GET
    @Path("/sun")
    public Response GetSatelliteSunExposures() throws CustomException {
        try {
            List<SunExposuresDTO> model = satelliteService.getSatelliteSunExposures();
            return Response.ok().entity(model).build();
        } catch(CustomException exception) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                  .type(MediaType.APPLICATION_JSON)
                  .entity(new CustomExceptionDTO(exception))
                  .build();
        }
    }

    /**
     * This method returns an object representing all the sun exposures from a given startTimestamp
     * to a given endTimestamp, with a pagination implementation to allow frontend to maximize performances. 
     * @return SunExposuresPaginationDTO 
     */
    @GET
    @Path("/sun/pagination")
    public Response GetSatelliteSunExposuresPagination(@QueryParam("pageNumber") int pageNumber, @QueryParam("pageSize") int pageSize) throws CustomException {
        try {
            SunExposuresPaginationDTO model = satelliteService.getSatelliteSunExposuresPagination(pageNumber, pageSize);
            return Response.ok().entity(model).build();
        } catch(CustomException exception) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                  .type(MediaType.APPLICATION_JSON)
                  .entity(new CustomExceptionDTO(exception))
                  .build();
        }
    }

}