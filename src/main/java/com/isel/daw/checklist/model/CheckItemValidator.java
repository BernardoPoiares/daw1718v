package com.isel.daw.checklist.model;

import com.isel.daw.checklist.ValidatorResponse;
import com.isel.daw.checklist.problems.InvalidAuthenticationProblem;
import org.springframework.http.HttpStatus;

import java.lang.Error;

public class CheckItemValidator {

    public static ValidatorResponse validateItemWithUser(String itemUser, String username){
        if(!itemUser.equals(username));
            //return new ValidatorResponse(false, new ResponseError(HttpStatus.BAD_REQUEST,"Item does not exist"));
        return new ValidatorResponse(true, null);
    }

    public static ValidatorResponse validateUser(Users user){
        if(user==null)
            return new ValidatorResponse(false,new InvalidAuthenticationProblem());
        return new ValidatorResponse(true,null);
    }
}
