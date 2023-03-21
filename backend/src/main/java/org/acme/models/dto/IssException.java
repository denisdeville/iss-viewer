package org.acme.models.dto;

public class IssException {
    private String error;
    private int statusCode;

    public IssException(){}

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
