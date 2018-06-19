package com.isel.daw.checklist.problems;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.http.HttpStatus;

public class ProblemJson {
    @JsonIgnore
    public final HttpStatus httpStatus;

    public final String type;
    public final String title;
    public final String detail;

    public ProblemJson(HttpStatus httpStatus,String type,String title,String detail){
        this.httpStatus=httpStatus;
        this.type=type;
        this.title=title;
        this.detail=detail;
    }
}
/*
public enum ProblemsJson {
    InvalidAuthenticationProblem
}*/