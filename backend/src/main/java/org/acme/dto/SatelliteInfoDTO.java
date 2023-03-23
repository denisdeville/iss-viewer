package org.acme.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class SatelliteInfoDTO {

    private int timestamp;
    private Double latitude;
    private Double longitude;

    public int getTimestamp() {
        return timestamp;
    }
    public void setTimestamp(int timestamp) {
        this.timestamp = timestamp;
    }
    @JsonIgnore
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
