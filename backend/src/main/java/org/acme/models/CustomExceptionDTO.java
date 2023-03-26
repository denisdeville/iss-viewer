package org.acme.models;

import org.acme.exceptions.CustomException;

public class CustomExceptionDTO {

    private String message;
    private int errorCode;

    public CustomExceptionDTO(CustomException exception)
    {
       this.message = exception.getMessage();
       this.errorCode = exception.getErrorCode();
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }
}


