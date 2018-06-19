package com.isel.daw.checklist.utils;

import org.springframework.http.HttpStatus;

public class ValidatorObj {
    private class Error{
        private HttpStatus httpStatus;
        private String message;
    }
    public final Error error=null;
}
