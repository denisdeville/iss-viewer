package org.acme.services.wheretheissat;

import java.net.http.HttpResponse;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;

import org.acme.HttpUtilities;
import org.acme.exceptions.CustomException;
import org.acme.models.wheretheissat.WhereTheIssAtSatelliteInfo;
import org.acme.models.wheretheissat.WhereTheIssAtTleData;

@ApplicationScoped
public class WhereTheIssAtService {
    
    private long issId = 25544;

    private final String whereTheIssAtBaseUrl =  "https://api.wheretheiss.at/v1/satellites/";

    public WhereTheIssAtSatelliteInfo GetIssCurrentPosition() throws CustomException
    {
        String url =  whereTheIssAtBaseUrl + issId;
        HttpResponse<String> response = HttpUtilities.getRequest(url);
        return HttpUtilities.handleResponse(response, WhereTheIssAtSatelliteInfo.class);
    }
    
    public List<WhereTheIssAtSatelliteInfo> GetIssPositionsByTimestamps(String timestamps) throws CustomException
    {
        String url = whereTheIssAtBaseUrl + issId + "/positions?timestamps=" + timestamps;
        HttpResponse<String> response = HttpUtilities.getRequest(url);
        return HttpUtilities.handleResponseList(response, WhereTheIssAtSatelliteInfo.class);
    }

    public WhereTheIssAtTleData GetIssTleData() throws CustomException
    {
        String url =  whereTheIssAtBaseUrl + issId + "/tles?format=json";
        HttpResponse<String> response = HttpUtilities.getRequest(url);
        return HttpUtilities.handleResponse(response, WhereTheIssAtTleData.class);
    }
}
