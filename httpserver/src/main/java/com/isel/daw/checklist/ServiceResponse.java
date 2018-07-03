package com.isel.daw.checklist;

import com.isel.daw.checklist.problems.ProblemJson;

public class ServiceResponse<T> {

    T response;
    ProblemJson error;

    public ServiceResponse(T resp,ProblemJson error){
        this.response=resp;
        this.error=error;
    }

    public T getResponse() {
        return response;
    }

    public ProblemJson getError() {
        return error;
    }
}
