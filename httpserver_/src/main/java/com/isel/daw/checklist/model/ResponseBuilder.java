package com.isel.daw.checklist.model;

import com.isel.daw.checklist.problems.ProblemJson;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseBuilder {

    public static ResponseEntity<?> build(Object object){
        HttpHeaders httpheaders=new HttpHeaders();
        httpheaders.add("Content-Type","application/vnd.siren+json");
        return new ResponseEntity<>(object, HttpStatus.ACCEPTED);
    }

    public static ResponseEntity<?> buildError(ProblemJson problem){
        HttpHeaders httpheaders=new HttpHeaders();
        httpheaders.add("Content-Type","application/problem+json");
        return new ResponseEntity<>(problem,httpheaders,problem.httpStatus);
    }
}
