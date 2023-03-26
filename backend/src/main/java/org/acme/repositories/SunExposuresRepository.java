package org.acme.repositories;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;

import org.acme.entities.SatelliteInfoHistoryEntity;
import org.acme.entities.SunExposuresEntity;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.panache.common.Page;

/**
 * The SunExposuresRepository class represents the data access layer for the SunExposuresEntity entity
 */
@ApplicationScoped
public class SunExposuresRepository {

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
