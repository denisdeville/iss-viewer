package org.acme.mapper;

import java.util.List;

import org.acme.dto.SatelliteInfoHistoryDTO;
import org.acme.entities.SatelliteInfoHistoryEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "cdi")
public interface SatelliteInfoHistoryMapper {
    SatelliteInfoHistoryDTO toResource(SatelliteInfoHistoryEntity user);
    SatelliteInfoHistoryEntity fromResource(SatelliteInfoHistoryDTO userDto);
    List<SatelliteInfoHistoryDTO> toDtoList(List<SatelliteInfoHistoryEntity> satelliteList);
}
