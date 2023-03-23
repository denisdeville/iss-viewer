package org.acme.services.wheretheissat;

import java.net.http.HttpResponse;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.acme.HttpUtilities;
import org.acme.exceptions.CustomException;
import org.acme.mapper.SatelliteInfoMapperInterface;
import org.acme.models.wheretheissat.WhereTheIssAtSatelliteInfo;
import org.acme.models.wheretheissat.WhereTheIssAtTleData;

@ApplicationScoped
public class WhereTheIssAtService {
    
    private final String whereTheIssAtBaseUrl =  "https://api.wheretheiss.at/v1/satellites/";

    @Inject
    SatelliteInfoMapperInterface satelliteInfoMapper;

    public WhereTheIssAtSatelliteInfo GetSatelliteInfoById(long id) throws CustomException
    {
        String url =  whereTheIssAtBaseUrl + id;
        HttpResponse<String> response = HttpUtilities.getRequest(url);
        return HttpUtilities.handleResponse(response, WhereTheIssAtSatelliteInfo.class);
    }
    
    public List<WhereTheIssAtSatelliteInfo> GetSatellitePositionByIdAndTimestamps(long id, String timestamps) throws CustomException
    {
        String url = whereTheIssAtBaseUrl + id + "/positions?timestamps=" + timestamps;
        HttpResponse<String> response = HttpUtilities.getRequest(url);
        return HttpUtilities.handleResponseList(response, WhereTheIssAtSatelliteInfo.class);
    }

    public WhereTheIssAtTleData GetTleDataById(long id) throws CustomException
    {
        String url =  whereTheIssAtBaseUrl + id + "/tles?format=json";
        HttpResponse<String> response = HttpUtilities.getRequest(url);
        return HttpUtilities.handleResponse(response, WhereTheIssAtTleData.class);
    }
}
