package org.acme.models.wheretheissat;

public class WhereTheIssAtException {
    private String error;
    private int statusCode;

    public WhereTheIssAtException(){}

    public String getError() {
        return error;
    }
    public void setError(String error) {
        this.error = error;
    }
    public int getStatusCode() {
        return statusCode;
    }
    public void setStatus(int status) {
        this.statusCode= status;
    }
}
