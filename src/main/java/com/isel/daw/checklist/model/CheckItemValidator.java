package com.isel.daw.checklist.model;

import com.isel.daw.checklist.ValidatorResponse;
import com.isel.daw.checklist.problems.InvalidAuthenticationProblem;
import com.isel.daw.checklist.problems.NotFoundProblem;
import org.springframework.http.HttpStatus;

import java.lang.Error;

public class CheckItemValidator {

    public static ValidatorResponse validateItemById(CheckItem checkItem, long id,Users user){
        if(user==null)
            return new ValidatorResponse(false,new InvalidAuthenticationProblem());
        if(checkItem==null || !(checkItem.getCheckitem_itemtemplate().getItemTemplate_user().getUserName().equals(user.getUserName())))
            return new ValidatorResponse(false, new NotFoundProblem("CheckItem with the id '"+id+"'"));
        return new ValidatorResponse(true,null);
    }

    public static ValidatorResponse validateUser(Users user){
        if(user==null)
            return new ValidatorResponse(false,new InvalidAuthenticationProblem());
        return new ValidatorResponse(true,null);
    }


}
