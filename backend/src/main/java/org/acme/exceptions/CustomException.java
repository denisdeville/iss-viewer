package org.acme.exceptions;

public class CustomException extends Exception {
    
    private int errorCode;
    
    // Parameterless Constructor
    public CustomException() {}

    // Constructor that accepts a message
    public CustomException(String message, int errorCode)
    {
       super(message);
       this.errorCode = errorCode;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    } 
}
