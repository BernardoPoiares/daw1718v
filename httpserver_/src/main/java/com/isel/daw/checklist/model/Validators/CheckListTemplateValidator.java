package com.isel.daw.checklist.model.Validators;

import com.isel.daw.checklist.ServiceResponse;
import com.isel.daw.checklist.ValidatorResponse;
import com.isel.daw.checklist.model.DataBaseDTOs.CheckListTemplate;
import com.isel.daw.checklist.model.RequestsDTO.CheckListTemplateRequestDto;
import com.isel.daw.checklist.model.DataBaseDTOs.Users;
import com.isel.daw.checklist.problems.*;

import java.util.List;

public class CheckListTemplateValidator {

    public static ValidatorResponse valUser(Users user){
        if(user==null)
            return new ValidatorResponse(false,new InvalidAuthenticationProblem());
         return new ValidatorResponse(true,null);
    }

    public static ValidatorResponse valUser_Id(Users user,long id){
        if(user==null)
            return new ValidatorResponse(false,new InvalidAuthenticationProblem());
        if(id<1)
            return new ValidatorResponse(false,new InvalidParameterProblem("id","The parameter 'id' must be higher then 0."));
        return new ValidatorResponse(true,null);
    }

    public static ValidatorResponse valCreateRequest(Users user,CheckListTemplateRequestDto checkListTemplateRequestDto){
        if(user==null)
            return new ValidatorResponse(false,new InvalidAuthenticationProblem());
      //  if(checkListTemplateRequestDto.getId()<1)
        //    return new ValidatorResponse(false,new InvalidParameterProblem("id","The parameter 'id' must be higher then 0."));
        if(checkListTemplateRequestDto.getName()==null)
            return new ValidatorResponse(false,new InvalidParameterProblem("name","The parameter 'name' must be not empty."));
        return new ValidatorResponse(true,null);
    }

    public static ValidatorResponse valUpdateResquest(Users user,CheckListTemplateRequestDto checkListTemplateRequestDto){
        if(user==null)
            return new ValidatorResponse(false,new InvalidAuthenticationProblem());
        if(checkListTemplateRequestDto.getId()<1)
            return new ValidatorResponse(false,new InvalidParameterProblem("id","The parameter 'id' must be higher then 0."));
        if(checkListTemplateRequestDto.getName()==null)
            return new ValidatorResponse(false,new InvalidParameterProblem("name","The parameter 'name' must be not empty."));
        return new ValidatorResponse(true,null);
    }

    public static ValidatorResponse validateListTemplate(CheckListTemplate checkListTemplate, Users user){
        if(checkListTemplate==null)
            return new ValidatorResponse(false,new InternalServerProblem());
        if(!(checkListTemplate.getListtemplate_user().getUserName().equals(user.getUserName()))) {
            return new ValidatorResponse(false, new NotFoundProblem("CheckList with the id '" + checkListTemplate.getId() + "'"));
        }return new ValidatorResponse(true,null);
    }

    public static ValidatorResponse validateListsTemplates(List<CheckListTemplate> checkListTemplates, Users user){
        if(checkListTemplates==null)
            return new ValidatorResponse(false,new InternalServerProblem());
        for(CheckListTemplate cklt:checkListTemplates){
            ValidatorResponse resp=validateListTemplate(cklt,user);
            if(!resp.isValid)
                return resp;
        }
        return new ValidatorResponse(true,null);
    }

    public static ValidatorResponse valCkItRequest(CheckListTemplateRequestDto checklisttemplate_dto) {
        if (checklisttemplate_dto == null)
            return new ValidatorResponse(false, new BadRequestProblem("checklisttemplate", "The object 'checklisttemplate' must not be empty'"));
        if (checklisttemplate_dto.getId() < 1)
            return new ValidatorResponse(false, new InvalidParameterProblem("id", "'id' must be bigger then 0."));
        if (checklisttemplate_dto.getCheckitemstemplates() == null)
            return new ValidatorResponse(false, new InvalidParameterProblem("checkitems", null));
        return new ValidatorResponse(true, null);
    }
}
