package org.acme.models.wheretheissat;

import java.math.BigInteger;

public class WhereTheIssAtSatelliteInfo implements Comparable<WhereTheIssAtSatelliteInfo> {

    private String name;
    private long id;
    private double latitude;
    private double longitude;
    private double altitude;
    private double velocity;
    private String visibility;
    private double footprint;
    private BigInteger timestamp;
    private double daynum;
    private double solar_lat;
    private double solar_lon;
    private String units;

    public WhereTheIssAtSatelliteInfo() {
    }

    public WhereTheIssAtSatelliteInfo(String name, long id, double latitude, double longitude, double altitude, double velocity,
            String visibility, double footprint, BigInteger timestamp, double daynum, double solar_lat, double solar_lon,
            String units) {
        this.name = name;
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.altitude = altitude;
        this.velocity = velocity;
        this.visibility = visibility;
        this.footprint = footprint;
        this.timestamp = timestamp;
        this.daynum = daynum;
        this.solar_lat = solar_lat;
        this.solar_lon = solar_lon;
        this.units = units;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public double getLatitude() {
        return latitude;
    }
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
    public double getLongitude() {
        return longitude;
    }
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
    public double getAltitude() {
        return altitude;
    }
    public void setAltitude(double altitude) {
        this.altitude = altitude;
    }
    public double getVelocity() {
        return velocity;
    }
    public void setVelocity(double velocity) {
        this.velocity = velocity;
    }
    public String getVisibility() {
        return visibility;
    }
    public void setVisibility(String visibility) {
        this.visibility = visibility;
    }
    public double getFootprint() {
        return footprint;
    }
    public void setFootprint(double footprint) {
        this.footprint = footprint;
    }
    public BigInteger getTimestamp() {
        return timestamp;
    }
    public void setTimestamp(BigInteger timestamp) {
        this.timestamp = timestamp;
    }
    public double getDaynum() {
        return daynum;
    }
    public void setDaynum(double daynum) {
        this.daynum = daynum;
    }
    public double getSolar_lat() {
        return solar_lat;
    }
    public void setSolar_lat(double solar_lat) {
        this.solar_lat = solar_lat;
    }
    public double getSolar_lon() {
        return solar_lon;
    }
    public void setSolar_lon(double solar_lon) {
        this.solar_lon = solar_lon;
    }
    public String getUnits() {
        return units;
    }
    public void setUnits(String units) {
        this.units = units;
    }

    @Override
    public String toString() {
        return "WhereTheIssAtSatelliteInfo [latitude=" + latitude + ", longitude=" + longitude + ", visibility="
                + visibility + ", timestamp=" + timestamp + "]";
    }

    @Override
    public int compareTo(WhereTheIssAtSatelliteInfo o) {
        return this.getTimestamp().compareTo(o.getTimestamp());
    }
}
