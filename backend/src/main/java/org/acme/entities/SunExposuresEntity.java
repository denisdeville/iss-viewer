package org.acme.entities;

import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

@Entity(name="sun_exposures")
public class SunExposuresEntity extends PanacheEntityBase {
    
    @Id
    @Column(name = "start_timestamp")
    private BigInteger startTimestamp;
    @Column(name = "end_timestamp")
    private BigInteger endTimestamp;
    private Double latitude;
    private Double longitude;

    public SunExposuresEntity(BigInteger startTimestamp, BigInteger endTimestamp, Double latitude, Double longitude) {
        this.startTimestamp = startTimestamp;
        this.endTimestamp = endTimestamp;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public SunExposuresEntity() {
    }

    public BigInteger getStartTimestamp() {
        return startTimestamp;
    }
    public void setStartTimestamp(BigInteger startTimestamp) {
        this.startTimestamp = startTimestamp;
    }
    public BigInteger getEndTimestamp() {
        return endTimestamp;
    }
    public void setEndTimestamp(BigInteger endTimestamp) {
        this.endTimestamp = endTimestamp;
    }
    public Double getLatitude() {
        return latitude;
    }
    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }
    public Double getLongitude() {
        return longitude;
    }
    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    
}
