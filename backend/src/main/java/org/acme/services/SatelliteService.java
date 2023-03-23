package org.acme.services;

import java.net.http.HttpResponse;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.acme.HttpUtilities;
import org.acme.dto.SatelliteInfoDTO;
import org.acme.entities.SatelliteInfoEntity;
import org.acme.exceptions.CustomException;
import org.acme.mapper.SatelliteInfoMapperInterface;
import org.acme.models.dto.SatelliteModel;

@ApplicationScoped
public class SatelliteService {
    
    private final String whereTheIssAtBaseUrl =  "https://api.wheretheiss.at/v1/satellites/";

    @Inject
    SatelliteInfoMapperInterface satelliteInfoMapper;

    public SatelliteModel GetSatelliteInformationById(long id) throws CustomException
    {
        String url =  whereTheIssAtBaseUrl + id;
        HttpResponse<String> response = HttpUtilities.getRequest(url);
        return HttpUtilities.handleResponse(response, SatelliteModel.class);
    }
    
    public SatelliteInfoDTO GetSatelliteInformationByTimestamp(int timestamp) throws CustomException
    {
        SatelliteInfoEntity entity = SatelliteInfoEntity.find("timestamp", timestamp).firstResult();

        if (entity != null) {
            return satelliteInfoMapper.toResource(entity);
        }

        return null;
    }
    
    public List<SatelliteModel> GetSatelliteSunExposionById(long id, String timestamps) throws CustomException
    {
        String url = whereTheIssAtBaseUrl + id + "/positions?timestamps=" + timestamps;
        HttpResponse<String> response = HttpUtilities.getRequest(url);
        return HttpUtilities.handleResponseList(response, SatelliteModel.class);
    }

    public SatelliteModel GetSatelliteInformationByIdMock(long id)
    {

        double randomLatitude = Math.random() * 120;
        double randomLongitude = Math.random() * 60;

        SatelliteModel satelliteModel = new SatelliteModel();
        satelliteModel.setLatitude(randomLatitude);
        satelliteModel.setLongitude(randomLongitude);

        return satelliteModel;
    }
}
