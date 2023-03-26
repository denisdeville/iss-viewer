package org.acme.repositories;

import java.math.BigInteger;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;

import org.acme.entities.SatelliteInfoHistoryEntity;
import org.acme.entities.SunExposuresEntity;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.panache.common.Page;
import io.quarkus.panache.common.Parameters;

/**
 * The SunExposuresRepository class represents the data access layer for the SunExposuresEntity entity
 */
@ApplicationScoped
public class SunExposuresRepository {

    public void save(SunExposuresEntity entity) {
        entity.persist();
    }

    public SunExposuresEntity getNotFinishedExposureEntity() {
        return SunExposuresEntity.find("select sunExp from sun_exposures sunExp where sunExp.endTimestamp is null")
                .firstResult();
    } 
    
    public void updateSunExposureSetEndTimestamp(BigInteger whereStartTimestamp, BigInteger newEndTimestamp) {
        SunExposuresEntity.update("UPDATE FROM sun_exposures SET endTimestamp = :endTimestamp where startTimestamp = :startTimestamp", Parameters.with("endTimestamp",newEndTimestamp).and("startTimestamp",whereStartTimestamp));
    }

    public void deleteAll() {
        SunExposuresEntity.deleteAll();
    }

    public List<SunExposuresEntity> findAll() {
        return SunExposuresEntity.listAll();
    }
    
    
    public List<SunExposuresEntity> findAllWithPagination(int pageNumber, int pageSize) {
        PanacheQuery<SunExposuresEntity> sunExposuresQuery = SatelliteInfoHistoryEntity.find("select sunExposures from sun_exposures sunExposures ORDER BY start_timestamp DESC");

        if (pageNumber < 0) {
            pageNumber = 0;
        }
        if (pageSize <= 0) {
            pageSize = 10;
        }

        List<SunExposuresEntity> sunExposures = sunExposuresQuery.page(Page.of(pageNumber, pageSize)).list();

        return sunExposures;
    }

    public int pageCount(int pageNumber, int pageSize) {
        PanacheQuery<SunExposuresEntity> sunExposuresQuery = SatelliteInfoHistoryEntity.find("select sunExposures from sun_exposures sunExposures ORDER BY start_timestamp DESC");

        if (pageNumber < 0) {
            pageNumber = 0;
        }
        if (pageSize <= 0) {
            pageSize = 10;
        }

        sunExposuresQuery.page(Page.of(pageNumber, pageSize));

        return sunExposuresQuery.pageCount();
    }
}
