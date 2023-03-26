package org.acme.repositories;

import java.math.BigInteger;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;

import org.acme.entities.SatelliteInfoHistoryEntity;

/**
 * The SatelliteInfoHistoryRepository class represents the data access layer for the SatelliteInfoHistoryEntity entity
 */
@ApplicationScoped
public class SatelliteInfoHistoryRepository {
    
    public void save(SatelliteInfoHistoryEntity entity) {
        entity.persist();
    }

    public void deleteAll() {
        SatelliteInfoHistoryEntity.deleteAll();
    }
    
    public List<SatelliteInfoHistoryEntity> getSatelliteInfoHistoryBetween(BigInteger startTimestamp,
            BigInteger endTimestamp) {

        List<SatelliteInfoHistoryEntity> satellitesInfos;
        if (endTimestamp != null) {
            satellitesInfos = SatelliteInfoHistoryEntity.list(
                    "select satellitesInfos from satellite_info_history satellitesInfos where satellitesInfos.timestamp BETWEEN "
                            + startTimestamp + " AND " + endTimestamp
                            + " ORDER BY timestamp ASC");
        } else {
            satellitesInfos = SatelliteInfoHistoryEntity.list(
                    "select satellitesInfos from satellite_info_history satellitesInfos where satellitesInfos.timestamp > "
                            + startTimestamp + " ORDER BY timestamp ASC");
        }

        return satellitesInfos;
    }
}
