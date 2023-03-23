package org.acme.dto;

import java.math.BigInteger;
import java.util.List;

public class SunExposuresDTO {
    private BigInteger startTimestamp;
    private BigInteger endTimestamp;
    private Double latitude;
    private Double longitude;
    private List<SatelliteInfoHistoryDTO> satelliteInfo;

    public SunExposuresDTO() {
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

    public List<SatelliteInfoHistoryDTO> getSatelliteInfo() {
        return satelliteInfo;
    }

    public void setSatelliteInfo(List<SatelliteInfoHistoryDTO> satelliteInfo) {
        this.satelliteInfo = satelliteInfo;
    }
    
}
