package com.isel.daw.checklist.problems;

import org.springframework.http.HttpStatus;

public class NotFoundProblem extends ProblemJson{

    public NotFoundProblem(String msg){
        super(HttpStatus.BAD_REQUEST,"https://x.probs/not-found-token","Resource Not Found.","The resource "+msg+" was not found.");
    }
}