package com.isel.daw.checklist.model;

import com.isel.daw.checklist.ValidatorResponse;
import com.isel.daw.checklist.problems.InvalidAuthenticationProblem;
import com.isel.daw.checklist.problems.InvalidParameterProblem;
import com.isel.daw.checklist.problems.NotFoundProblem;

public class CheckItemTemplateValidator {
    public static ValidatorResponse validateDeltReqTempList(CheckItemTemplate checkItemtemplate,Users user,CheckListTemplate checklisttemplate){
        if(user==null)
            return new ValidatorResponse(false,new InvalidAuthenticationProblem());
        if(checkItemtemplate==null || !(checkItemtemplate.getItemTemplate_user().getUserName().equals(user.getUserName()))
                ||checkItemtemplate.getCheckitemtemplate_checklisttemplate()!=checklisttemplate)
            return new ValidatorResponse(false, new NotFoundProblem("CheckItemTemplate with the id '" + checkItemtemplate.getId() + "'"));
        return new ValidatorResponse(true,null);
    }

}
