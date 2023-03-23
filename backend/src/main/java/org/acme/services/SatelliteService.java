package org.acme.services;

import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.acme.dto.SunExposuresDTO;
import org.acme.entities.SatelliteInfoHistoryEntity;
import org.acme.entities.SunExposuresEntity;
import org.acme.exceptions.CustomException;
import org.acme.mapper.SatelliteInfoHistoryMapper;
import org.acme.mapper.SatelliteInfoMapperInterface;
import org.acme.mapper.SunExposuresMapper;
import org.acme.models.dto.IssCoordinates;

@ApplicationScoped
public class SatelliteService {
    
    @Inject
    SatelliteInfoMapperInterface satelliteInfoMapper;

    @Inject
    SunExposuresMapper sunExposuresMapper;

    @Inject
    SatelliteInfoHistoryMapper satelliteInfoHistoryMapper;


    public IssCoordinates GetSatelliteCurrentPosition() throws CustomException
    {
        IssCoordinates currentSatteliteCoordinates = TLEService.getInstance().getLatitudeLongitude(LocalDateTime.now(ZoneOffset.UTC));
        if (currentSatteliteCoordinates != null) {
            return currentSatteliteCoordinates;
        } else {
            throw new CustomException("Unable to fetch current position of ISS from TLE propagation", 500);
        }
    }
    
    public List<SunExposuresDTO> GetSatelliteSunExposures() throws CustomException
    {
        List<SunExposuresEntity> sunExposures = SunExposuresEntity.listAll();
        List<SunExposuresDTO> sunExposuresDTOs = sunExposuresMapper.toDtoList(sunExposures);

        for(SunExposuresDTO sunExposure : sunExposuresDTOs) {
            List<SatelliteInfoHistoryEntity> satellitesInfos = SatelliteInfoHistoryEntity.list("select satellitesInfos from satellite_info_history satellitesInfos where satellitesInfos.timestamp BETWEEN " + sunExposure.getStartTimestamp() + " AND " + sunExposure.getEndTimestamp() + " ORDER BY timestamp ASC");
            sunExposure.setSatelliteInfo(satelliteInfoHistoryMapper.toDtoList(satellitesInfos));
        }
        return sunExposuresDTOs;
    }
}
