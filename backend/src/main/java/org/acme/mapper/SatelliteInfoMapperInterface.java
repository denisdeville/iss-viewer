package org.acme.mapper;

import org.acme.dto.SatelliteInfoDTO;
import org.acme.entities.SatelliteInfoEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "cdi")
public interface SatelliteInfoMapperInterface {
    SatelliteInfoDTO toResource(SatelliteInfoEntity user);
    SatelliteInfoEntity fromResource(SatelliteInfoDTO userDto);
}
