package com.isel.daw.checklist.model;

import org.springframework.http.HttpStatus;

public class ResponseError {
    public final String message;
    public final HttpStatus httpStatus;

    public ResponseError(HttpStatus httpStatus, String message) {
        this.message = message;
        this.httpStatus = httpStatus;
    }
}
