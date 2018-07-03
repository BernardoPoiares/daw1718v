package com.isel.daw.checklist;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
class ExceptionController {

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<?> defaultErrorHandler(HttpServletRequest req, Exception e) throws Exception {
        return new ResponseEntity(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}