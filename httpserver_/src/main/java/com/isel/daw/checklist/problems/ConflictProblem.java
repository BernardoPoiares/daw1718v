package com.isel.daw.checklist.problems;

import org.springframework.http.HttpStatus;

public class ConflictProblem extends  ProblemJson{
        public ConflictProblem(String msg){
            super(HttpStatus.CONFLICT,"https://x.probs/conflit","Conflict request",msg);
        }
}
