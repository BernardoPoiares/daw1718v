package com.isel.daw.checklist.model;

import com.isel.daw.checklist.ValidatorResponse;
import com.isel.daw.checklist.problems.InvalidAuthenticationProblem;

public class CheckListValidator {

    public static ValidatorResponse validateUser(Users user){
        if(user==null)
            return new ValidatorResponse(false,new InvalidAuthenticationProblem());
        return new ValidatorResponse(true,null);
    }

}
