package com.isel.daw.checklist.problems;

import org.springframework.http.HttpStatus;

public class BadRequestProblem extends ProblemJson{

    public BadRequestProblem(String objName,String msg){
        super(HttpStatus.BAD_REQUEST,"https://x.probs/bad-request","Object given is invalid.","The object"+ objName+" passed is invalid."+msg);
    }
}
