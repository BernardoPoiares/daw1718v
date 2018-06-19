package com.isel.daw.checklist.problems;

import org.springframework.http.HttpStatus;

public class InternalServerProblem  extends ProblemJson{

    public InternalServerProblem(){
        super(HttpStatus.INTERNAL_SERVER_ERROR,"https://x.probs/internal-server-error","Internal Server Error.","There was an internal server error.");
    }
}
