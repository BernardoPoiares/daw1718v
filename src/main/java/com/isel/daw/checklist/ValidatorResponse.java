package com.isel.daw.checklist;

import com.isel.daw.checklist.problems.ProblemJson;

public class ValidatorResponse {

    public final boolean isValid;
    public final ProblemJson problem;

    public ValidatorResponse(boolean isValid, ProblemJson problem) {
        this.isValid = isValid;
        this.problem = problem;
    }
}
