package com.nscooper.hsbc.library.controllers.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public class ExceptionResponseDto {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
    private LocalDateTime timestamp;

    public ExceptionResponseDto(LocalDateTime timestamp, HttpStatus status, String error) {
        this.timestamp = timestamp;
        this.status = status;
        this.error = error;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    private HttpStatus status;
    private String error;

}
