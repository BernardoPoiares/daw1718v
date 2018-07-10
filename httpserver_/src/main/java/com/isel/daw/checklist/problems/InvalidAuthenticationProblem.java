package com.isel.daw.checklist.problems;

import org.springframework.http.HttpStatus;

public class InvalidAuthenticationProblem extends ProblemJson{

    public InvalidAuthenticationProblem(){
        super(HttpStatus.BAD_REQUEST,"https://x.probs/invalid-auth-token","Authentication Token given invalid.","The authentication token passed on the header Authentication is invalid.");
    }
}
