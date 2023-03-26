package org.acme.services;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.acme.dto.IssCoordinates;
import org.acme.dto.SunExposuresDTO;
import org.acme.dto.SunExposuresPaginationDTO;
import org.acme.entities.SunExposuresEntity;
import org.acme.exceptions.CustomException;
import org.acme.mapper.SatelliteInfoHistoryMapper;
import org.acme.mapper.SunExposuresMapper;
import org.acme.repositories.SatelliteInfoHistoryRepository;
import org.acme.repositories.SunExposuresRepository;

/**
 * The SatelliteService class represents the business logic layer of the
 * application.
 */
@ApplicationScoped
public class SatelliteService {

    @Inject
    SunExposuresMapper sunExposuresMapper;

    @Inject
    SatelliteInfoHistoryMapper satelliteInfoHistoryMapper;

    @Inject
    SunExposuresRepository sunExposuresRepository;

    @Inject
    SatelliteInfoHistoryRepository satelliteInfoHistoryRepository;

    public IssCoordinates GetSatelliteCurrentPosition() throws CustomException {
        IssCoordinates currentSatteliteCoordinates = TLEService.getInstance()
                .getLatitudeLongitude(LocalDateTime.now(ZoneOffset.UTC));
        if (currentSatteliteCoordinates != null) {
            return currentSatteliteCoordinates;
        } else {
            throw new CustomException("Unable to fetch current position of ISS from TLE propagation", 500);
        }
    }

    public List<SunExposuresDTO> GetSatelliteSunExposures() throws CustomException {
        List<SunExposuresEntity> sunExposures = sunExposuresRepository.findAll();
        List<SunExposuresDTO> sunExposuresDTOs = sunExposuresMapper.toDtoList(sunExposures);

        // A foreign key implementation would have been better but I am lacking some time. 
        for (SunExposuresDTO sunExposure : sunExposuresDTOs) {
            sunExposure.setSatelliteInfo(satelliteInfoHistoryMapper.toDtoList(satelliteInfoHistoryRepository
                    .getSatelliteInfoHistoryBetween((sunExposure.getStartTimestamp()), sunExposure.getEndTimestamp())));
        }
        return sunExposuresDTOs;
    }

    public SunExposuresPaginationDTO GetSatelliteSunExposuresPagination(int pageNumber, int pageSize)
            throws CustomException {
        List<SunExposuresEntity> sunExposures = sunExposuresRepository.findAllWithPagination(pageNumber, pageSize);

        int pageCount = sunExposuresRepository.pageCount(pageNumber, pageSize);

        List<SunExposuresDTO> sunExposuresDTOs = sunExposuresMapper.toDtoList(sunExposures);

        // A foreign key implementation would have been better but I am lacking some time. 
        for (SunExposuresDTO sunExposure : sunExposuresDTOs) {
            sunExposure.setSatelliteInfo(satelliteInfoHistoryMapper.toDtoList(satelliteInfoHistoryRepository
                    .getSatelliteInfoHistoryBetween((sunExposure.getStartTimestamp()), sunExposure.getEndTimestamp())));
        }

        return new SunExposuresPaginationDTO(sunExposuresDTOs, pageCount);
    }
}
