package com.isel.daw.checklist.model;

import com.isel.daw.checklist.ValidatorResponse;
import com.isel.daw.checklist.model.RequestsDTO.CheckListRequestDto;
import com.isel.daw.checklist.model.RequestsDTO.CheckListTemplateRequestDto;
import com.isel.daw.checklist.problems.InvalidAuthenticationProblem;
import com.isel.daw.checklist.problems.InvalidMultiParameterProblem;
import com.isel.daw.checklist.problems.InvalidParameterProblem;
import com.isel.daw.checklist.problems.NotFoundProblem;

public class CheckListTemplateValidator {

    public static ValidatorResponse validateUser(Users user){
        if(user==null)
            return new ValidatorResponse(false,new InvalidAuthenticationProblem());
        return new ValidatorResponse(true,null);
    }


    public static ValidatorResponse validateListTemplateById(CheckListTemplate checkListTemplate, long id,Users user){
        if(user==null)
            return new ValidatorResponse(false,new InvalidAuthenticationProblem());
        if(checkListTemplate==null || !(checkListTemplate.getListtemplate_user().getUserName().equals(user.getUserName()))) {
            if(id<1)
                return new ValidatorResponse(false,new InvalidParameterProblem("id","'id' must be bigger then 0."));
            return new ValidatorResponse(false, new NotFoundProblem("CheckList with the id '" + id + "'"));
        }return new ValidatorResponse(true,null);
    }

    public static ValidatorResponse validateListTemplateRequest(CheckListTemplateRequestDto checklisttemplate_dto){
        if(checklisttemplate_dto==null)
            return new ValidatorResponse(false,new InvalidAuthenticationProblem());
        if(checklisttemplate_dto.getId()<1)
            return new ValidatorResponse(false,new InvalidParameterProblem("id","'id' must be bigger then 0."));
        if(checklisttemplate_dto.getName()==null)
            return new ValidatorResponse(false,new InvalidParameterProblem("name",null));
        return new ValidatorResponse(true,null);
    }

    public static ValidatorResponse validateListTemplatetoAddItemsRequest(CheckListTemplateRequestDto checklisttemplate_dto){
        if(checklisttemplate_dto==null)
            return new ValidatorResponse(false,new InvalidAuthenticationProblem());
        if(checklisttemplate_dto.getId()<1)
            return new ValidatorResponse(false,new InvalidParameterProblem("id","'id' must be bigger then 0."));
        if(checklisttemplate_dto.getCheckitems()==null)
            return new ValidatorResponse(false,new InvalidParameterProblem("checkitems",null));
        return new ValidatorResponse(true,null);
    }
}
