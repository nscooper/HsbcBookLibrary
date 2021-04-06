package com.nscooper.hsbc.library.exceptions;

import org.springframework.http.HttpStatus;

public class LibraryException extends Exception {


    private static final long serialVersionUID = -5506655538460800861L;

    private int errorCode;
    private String errorMessage;

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }




    public LibraryException(Throwable throwable) {
        super(throwable);
        this.errorMessage = throwable.getLocalizedMessage();
    }

    public LibraryException(String msg, Throwable throwable) {
        super(msg, throwable);
        this.errorMessage = checkForHttpErrorCodeAndGetMeaningText(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                throwable.getLocalizedMessage());
    }


    public LibraryException(String msg, int errorCode, Throwable throwable) {
        super(msg, throwable);
        this.errorCode = errorCode;
        this.errorMessage = checkForHttpErrorCodeAndGetMeaningText(errorCode, msg);
    }

    public LibraryException(String msg) {
        super(msg);
        this.errorMessage = msg;
    }

    public LibraryException(String msg, int errorCode) {
        super();
        this.errorCode = errorCode;
        this.errorMessage = checkForHttpErrorCodeAndGetMeaningText(errorCode, msg);
    }

    private String checkForHttpErrorCodeAndGetMeaningText(final int errorCode, final String msg) {
        HttpStatus httpStatus;
        try {
            httpStatus = HttpStatus.valueOf(errorCode);
            return httpStatus.getReasonPhrase() + ". " + msg;
        } catch(IllegalArgumentException e) {
            return msg;
        }
    }

    @Override
    public final String toString() {
        return this.errorCode + " " + this.errorMessage;
    }

}