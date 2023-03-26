package org.acme.services;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.acme.dto.IssCoordinates;
import org.acme.dto.SunExposuresDTO;
import org.acme.dto.SunExposuresPaginationDTO;
import org.acme.entities.SatelliteInfoHistoryEntity;
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
    private SunExposuresMapper sunExposuresMapper;

    @Inject
    private SatelliteInfoHistoryMapper satelliteInfoHistoryMapper;

    @Inject
    private SunExposuresRepository sunExposuresRepository;

    @Inject
    private SatelliteInfoHistoryRepository satelliteInfoHistoryRepository;

    public IssCoordinates getSatelliteCurrentPosition() throws CustomException {
        IssCoordinates currentSatteliteCoordinates = TLEService.getInstance()
                .getLatitudeLongitude(LocalDateTime.now(ZoneOffset.UTC));
        if (currentSatteliteCoordinates != null) {
            return currentSatteliteCoordinates;
        } else {
            throw new CustomException("Unable to fetch current position of ISS from TLE propagation", 500);
        }
    }

    public List<SunExposuresDTO> getSatelliteSunExposures() throws CustomException {
        List<SunExposuresEntity> sunExposures = sunExposuresRepository.findAll();
        List<SunExposuresDTO> sunExposuresDTOs = sunExposuresMapper.toDtoList(sunExposures);

        // A foreign key implementation would have been better but I am lacking some
        // time.
        for (SunExposuresDTO sunExposure : sunExposuresDTOs) {
            sunExposure.setSatelliteInfo(satelliteInfoHistoryMapper.toDtoList(satelliteInfoHistoryRepository
                    .getSatelliteInfoHistoryBetween((sunExposure.getStartTimestamp()), sunExposure.getEndTimestamp())));
        }
        return sunExposuresDTOs;
    }

    public SunExposuresPaginationDTO getSatelliteSunExposuresPagination(int pageNumber, int pageSize)
            throws CustomException {
        List<SunExposuresEntity> sunExposures = sunExposuresRepository.findAllWithPagination(pageNumber, pageSize);

        int pageCount = sunExposuresRepository.pageCount(pageNumber, pageSize);

        List<SunExposuresDTO> sunExposuresDTOs = sunExposuresMapper.toDtoList(sunExposures);

        // A foreign key implementation would have been better but I am lacking some
        // time.
        for (SunExposuresDTO sunExposure : sunExposuresDTOs) {
            sunExposure.setSatelliteInfo(satelliteInfoHistoryMapper.toDtoList(satelliteInfoHistoryRepository
                    .getSatelliteInfoHistoryBetween((sunExposure.getStartTimestamp()), sunExposure.getEndTimestamp())));
        }

        return new SunExposuresPaginationDTO(sunExposuresDTOs, pageCount);
    }

    public void addNewSunExposure(SunExposuresEntity sunExposuresEntity) {
        this.sunExposuresRepository.save(sunExposuresEntity);
    }

    public void saveAllSatelliteInfoHistory(List<SatelliteInfoHistoryEntity> entities) {
        this.satelliteInfoHistoryRepository.saveAll(entities);
    }

    public SunExposuresEntity getNotFinishedSunExposuresEntity() {
        return this.sunExposuresRepository.getNotFinishedExposureEntity();
    }

    public void updateSunExposureSetEndTimestamp(BigInteger startTimestamp, BigInteger endTimestamp) {
        this.sunExposuresRepository.updateSunExposureSetEndTimestamp(startTimestamp, endTimestamp);
    }
}
