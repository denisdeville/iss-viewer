package org.acme.models.wheretheissat;

public class WhereTheIssAtTleData {
    private double requested_timestamp;
    private double tle_timestamp;
    private String name;
    private int id;
    private String header;
    private String line1;
    private String line2;

    public WhereTheIssAtTleData() {
    }

    public double getRequested_timestamp() {
        return requested_timestamp;
    }
    public void setRequested_timestamp(double requested_timestamp) {
        this.requested_timestamp = requested_timestamp;
    }
    public double getTle_timestamp() {
        return tle_timestamp;
    }
    public void setTle_timestamp(double tle_timestamp) {
        this.tle_timestamp = tle_timestamp;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getHeader() {
        return header;
    }
    public void setHeader(String header) {
        this.header = header;
    }
    public String getLine1() {
        return line1;
    }
    public void setLine1(String line1) {
        this.line1 = line1;
    }
    public String getLine2() {
        return line2;
    }
    public void setLine2(String line2) {
        this.line2 = line2;
    }

    @Override
    public String toString() {
        return "WhereTheIssAtTleData [requested_timestamp=" + requested_timestamp + ", tle_timestamp=" + tle_timestamp
                + ", name=" + name + ", id=" + id + ", header=" + header + ", line1=" + line1 + ", line2=" + line2
                + "]";
    }  
}
