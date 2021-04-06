package com.nscooper.hsbc.library.exceptions;

import com.google.common.collect.ImmutableMap;
import com.nscooper.hsbc.library.controllers.dto.ExceptionResponseDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;


@ControllerAdvice
public class LibraryExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(LibraryExceptionHandler.class);

    @ExceptionHandler(LibraryException.class)
    public ResponseEntity<ExceptionResponseDto> customHandleNotFound(LibraryException ex, WebRequest request) {

        ExceptionResponseDto errors = new ExceptionResponseDto(LocalDateTime.now(), HttpStatus.BAD_REQUEST,
                ex.getLocalizedMessage());

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }


}