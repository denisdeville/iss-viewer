package org.acme.entities;

import java.math.BigInteger;

import javax.persistence.Entity;
import javax.persistence.Id;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

@Entity(name="satellite_info_history")
public class SatelliteInfoHistoryEntity extends PanacheEntityBase {
    
    @Id
    private BigInteger timestamp;
    private Double latitude;
    private Double longitude;
    private String visibility;

    public SatelliteInfoHistoryEntity() {
    }

    public SatelliteInfoHistoryEntity(BigInteger timestamp, Double latitude, Double longitude, String visibility) {
        this.timestamp = timestamp;
        this.latitude = latitude;
        this.longitude = longitude;
        this.visibility = visibility;
    }

    public BigInteger getTimestamp() {
        return timestamp;
    }
    public void setTimestamp(BigInteger timestamp) {
        this.timestamp = timestamp;
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
    public String getVisibility() {
        return visibility;
    }
    public void setVisibility(String visibility) {
        this.visibility = visibility;
    }
    
}
