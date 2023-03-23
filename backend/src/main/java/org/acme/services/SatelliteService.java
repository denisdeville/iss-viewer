package org.acme.services;

import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.acme.HttpUtilities;
import org.acme.exceptions.CustomException;
import org.acme.mapper.SatelliteInfoMapperInterface;
import org.acme.models.dto.IssCoordinates;
import org.acme.models.wheretheissat.WhereTheIssAtSatelliteInfo;

@ApplicationScoped
public class SatelliteService {
    
    private final String whereTheIssAtBaseUrl =  "https://api.wheretheiss.at/v1/satellites/";

    @Inject
    SatelliteInfoMapperInterface satelliteInfoMapper;

    public IssCoordinates GetSatelliteCurrentPosition(long id) throws CustomException
    {
        IssCoordinates currentSatteliteCoordinates = TLEService.getInstance().getLatitudeLongitude(LocalDateTime.now(ZoneOffset.UTC));
        if (currentSatteliteCoordinates != null) {
            return currentSatteliteCoordinates;
        } else {
            throw new CustomException("Unable to fetch current position of ISS from TLE propagation", 500);
        }
    }
    
    public List<WhereTheIssAtSatelliteInfo> GetSatelliteSunExposionById(long id, String timestamps) throws CustomException
    {
        String url = whereTheIssAtBaseUrl + id + "/positions?timestamps=" + timestamps;
        HttpResponse<String> response = HttpUtilities.getRequest(url);
        return HttpUtilities.handleResponseList(response, WhereTheIssAtSatelliteInfo.class);
    }
}
