package com.isel.daw.checklist.problems;

import org.springframework.http.HttpStatus;

public class InvalidMultiParameterProblem extends ProblemJson{

    public InvalidMultiParameterProblem(String[] paramNames){
        super(HttpStatus.BAD_REQUEST,"https://x.probs/invalid-multi-params","More then one parameter are incorrect.","The parameters:'"+paramNames.toString()+"' are invalid.");
    }

}
