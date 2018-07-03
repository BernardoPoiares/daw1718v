package com.isel.daw.checklist.problems;

import org.springframework.http.HttpStatus;

public class InvalidParameterProblem extends ProblemJson{

    public InvalidParameterProblem(String objName,String msg){
        super(HttpStatus.BAD_REQUEST,"https://x.probs/invalid-parameter","Parameter given is invalid.","The parameter"+ objName+" passed is invalid"+msg);
    }
}