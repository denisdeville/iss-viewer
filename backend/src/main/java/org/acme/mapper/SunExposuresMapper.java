package org.acme.mapper;

import java.util.List;

import org.acme.dto.SunExposuresDTO;
import org.acme.entities.SunExposuresEntity;
import org.mapstruct.Mapper;


@Mapper(componentModel = "cdi")
public interface SunExposuresMapper {
    SunExposuresDTO toResource(SunExposuresEntity user);
    SunExposuresEntity fromResource(SunExposuresDTO userDto);
    List<SunExposuresDTO> toDtoList(List<SunExposuresEntity> enterpriseEntityList);
}
