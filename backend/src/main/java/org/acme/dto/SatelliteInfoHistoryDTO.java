package org.acme.dto;

public class SatelliteInfoHistoryDTO {
    
    private Double latitude;
    private Double longitude;

    public SatelliteInfoHistoryDTO() {
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
