package com.isel.daw.checklist.model.Validators;

import com.isel.daw.checklist.ValidatorResponse;
import com.isel.daw.checklist.model.DataBaseDTOs.CheckItemTemplate;
import com.isel.daw.checklist.model.DataBaseDTOs.CheckListTemplate;
import com.isel.daw.checklist.model.DataBaseDTOs.Users;
import com.isel.daw.checklist.model.RequestsDTO.CheckItemTemplateRequestDto;
import com.isel.daw.checklist.problems.BadRequestProblem;
import com.isel.daw.checklist.problems.InvalidAuthenticationProblem;
import com.isel.daw.checklist.problems.InvalidParameterProblem;
import com.isel.daw.checklist.problems.NotFoundProblem;

public class CheckItemTemplateValidator {
    public static ValidatorResponse validateDeltReqTempList(CheckItemTemplate checkItemtemplate, Users user, CheckListTemplate checklisttemplate){
        if(user==null)
            return new ValidatorResponse(false,new InvalidAuthenticationProblem());
        if(checkItemtemplate==null || !(checkItemtemplate.getItemTemplate_user().getUserName().equals(user.getUserName()))
                ||checkItemtemplate.getCheckitemtemplate_checklisttemplate()!=checklisttemplate)
            return new ValidatorResponse(false, new NotFoundProblem("CheckItemTemplate with the id '" + checkItemtemplate.getId() + "'"));
        return new ValidatorResponse(true,null);
    }

    public static ValidatorResponse validateCreateRequest(CheckItemTemplateRequestDto checkItemtemplate, Users user){
        if(user==null)
            return new ValidatorResponse(false,new InvalidAuthenticationProblem());
        if(checkItemtemplate==null)
            return new ValidatorResponse(false, new BadRequestProblem("checkitemtemplate","Object is null"));
        if(checkItemtemplate.getName()==null)
            return new ValidatorResponse(false, new InvalidParameterProblem("name",null));
        return new ValidatorResponse(true,null);
    }
}
