package com.isel.daw.checklist.model.Validators;

import com.isel.daw.checklist.ValidatorResponse;
import com.isel.daw.checklist.model.DataBaseDTOs.CheckItem;
import com.isel.daw.checklist.model.DataBaseDTOs.CheckItemTemplate;
import com.isel.daw.checklist.model.DataBaseDTOs.CheckListTemplate;
import com.isel.daw.checklist.model.DataBaseDTOs.Users;
import com.isel.daw.checklist.model.RequestsDTO.CheckItemTemplateRequestDto;
import com.isel.daw.checklist.model.RequestsDTO.CheckListTemplateRequestDto;
import com.isel.daw.checklist.problems.*;

import java.util.List;

public class CheckItemTemplateValidator {

    public static ValidatorResponse valRequest(CheckItemTemplateRequestDto ckit_dto, Users user){
        if(user==null)
            return new ValidatorResponse(false,new InvalidAuthenticationProblem());
        if (ckit_dto == null)
            return new ValidatorResponse(false, new BadRequestProblem("checkitemtemplate", "The object 'checkitemtemplate' must not be empty'"));
        if (ckit_dto.getId() < 1)
            return new ValidatorResponse(false, new InvalidParameterProblem("id", "'id' must be bigger then 0."));
        return new ValidatorResponse(true, null);
    }

    public static ValidatorResponse validateUpdateRequest(CheckItemTemplateRequestDto ckit_dto){
        if (ckit_dto == null)
            return new ValidatorResponse(false, new BadRequestProblem("checkitemtemplate", "The object 'checkitemtemplate' must not be empty'"));
        if(ckit_dto.getName()==null && ckit_dto.getDescription()==null){
            return new ValidatorResponse(false,new InvalidMultiParameterProblem("description,name"));
        }
        return new ValidatorResponse(true,null);
    }

    public static ValidatorResponse validateDeltReqTempList(CheckItemTemplate checkItemtemplate, Users user, CheckListTemplate checklisttemplate){
        if(user==null)
            return new ValidatorResponse(false,new InvalidAuthenticationProblem());
        if(checkItemtemplate==null || !(checkItemtemplate.getItemTemplate_user().getUserName().equals(user.getUserName()))
                ||checkItemtemplate.getCheckitemtemplate_checklisttemplate()!=checklisttemplate)
            return new ValidatorResponse(false, new NotFoundProblem("CheckItemTemplate with the id '" + checkItemtemplate.getId() + "'"));
        return new ValidatorResponse(true,null);
    }

    public static ValidatorResponse validateId(long id){
        if(id<1)
            return new ValidatorResponse(false,new InvalidParameterProblem("id","'id' must be bigger then 0."));
        return new ValidatorResponse(true,null);
    }
    public static ValidatorResponse validateItem(CheckItemTemplate checkItemTemplate,Users user){
        if(checkItemTemplate==null)
            return new ValidatorResponse(false, new InternalServerProblem());
        if(checkItemTemplate.getItemTemplate_user().getId()!=user.getId())
            return new ValidatorResponse(false, new NotFoundProblem("Not found"));
        return new ValidatorResponse(true,null);
    }

    public static ValidatorResponse validateItems(List<CheckItemTemplate> checkListTemplates, Users user){
        if(checkListTemplates==null)
            return  new ValidatorResponse(false,new InternalServerProblem());
        for(CheckItemTemplate checkItemTemplate:checkListTemplates){
            ValidatorResponse resp=validateItem(checkItemTemplate,user);
            if(!resp.isValid)
                return resp;
        }
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
