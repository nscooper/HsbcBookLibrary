package com.nscooper.hsbc.library.exceptions;

public class LibraryException extends Exception {


    private final String errorCode;

    public LibraryException(String message) {
        super(message);
        errorCode = null;
    }

    public LibraryException(String pMessage, String errorCode) {
        super(pMessage);
        this.errorCode = errorCode;
    }

    public LibraryException(String pMessage, Throwable cause) {
        super(pMessage, cause);
        errorCode = null;
    }

    public LibraryException(String pMessage, String errorCode, Throwable cause) {
        super(pMessage, cause);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }
}
