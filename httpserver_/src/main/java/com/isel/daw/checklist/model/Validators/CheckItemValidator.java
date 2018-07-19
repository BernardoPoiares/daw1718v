package com.isel.daw.checklist.model.Validators;

import com.isel.daw.checklist.ValidatorResponse;
import com.isel.daw.checklist.model.DataBaseDTOs.CheckItem;
import com.isel.daw.checklist.model.DataBaseDTOs.CheckItemTemplate;
import com.isel.daw.checklist.model.RequestsDTO.CheckItemRequestDto;
import com.isel.daw.checklist.model.DataBaseDTOs.Users;
import com.isel.daw.checklist.model.RequestsDTO.CheckItemTemplateRequestDto;
import com.isel.daw.checklist.problems.*;

public class CheckItemValidator {

    public static ValidatorResponse validateItem(CheckItem checkItem, Users user){
        if(checkItem==null)
            return new ValidatorResponse(false,new InternalServerProblem());
        if(!(checkItem.getCheckitem_itemtemplate().getItemTemplate_user().getId()==user.getId())) {
            return new ValidatorResponse(false, new NotFoundProblem("CheckItem with the id '" + checkItem.getId()+ "'"));
        }return new ValidatorResponse(true,null);
    }

    public static ValidatorResponse validateUser_Id(Users user,long id){
        if(user==null)
            return new ValidatorResponse(false,new InvalidAuthenticationProblem());
        if(id<1)
            return new ValidatorResponse(false,new InvalidParameterProblem("id","'id' must be bigger then 0."));
        return new ValidatorResponse(true,null);
    }

    public static ValidatorResponse validateUser(Users user){
        if(user==null)
            return new ValidatorResponse(false,new InvalidAuthenticationProblem());
        return new ValidatorResponse(true,null);
    }


    public static ValidatorResponse validateItemUpdateRequest(CheckItemRequestDto checkitem_dto){
        if(checkitem_dto==null)
            return new ValidatorResponse(false, new BadRequestProblem("checkitem", "The object 'checkitem' must not be empty'"));
        if(checkitem_dto.getId()<1)
            return new ValidatorResponse(false,new InvalidParameterProblem("id","'id' must be bigger then 0."));
        if(checkitem_dto.getCheckitemtemplate().getDescription()==null && checkitem_dto.getCheckitemtemplate().getName()==null && checkitem_dto.getState()==null)
            return new ValidatorResponse(false,new InvalidMultiParameterProblem("description,name,state"));
        return new ValidatorResponse(true,null);
    }

    public static ValidatorResponse validateCkitDto(CheckItemRequestDto checkitem_dto){
        if(checkitem_dto==null)
            return new ValidatorResponse(false, new BadRequestProblem("checkitem", "The object 'checkitem' must not be empty'"));
        if(checkitem_dto.getCheckitemtemplate()==null || checkitem_dto.getCheckitemtemplate().getDescription()==null && checkitem_dto.getCheckitemtemplate().getName()==null && checkitem_dto.getState()==null)
            return new ValidatorResponse(false,new InvalidMultiParameterProblem("description,name,state"));
        return new ValidatorResponse(true,null);
    }


    public static ValidatorResponse validatCkitTemp(CheckItemTemplateRequestDto checkItemTemplateRequest_dto){
        if(checkItemTemplateRequest_dto==null)
            return new ValidatorResponse(false, new BadRequestProblem("checkitemtemplate", "The object 'checkitemtemplate' must not be empty'"));
        if(checkItemTemplateRequest_dto.getId()<1)
            return new ValidatorResponse(false,new InvalidParameterProblem("id","'id' must be bigger then 0."));
        return new ValidatorResponse(true,null);
    }


    public static ValidatorResponse validateItems(CheckItem[] checkItems, Users user){
        if(checkItems==null)
            return new ValidatorResponse(false, new NotFoundProblem("There are no checkitems for the user '"+user.getUserName()+"'"));
        for (CheckItem checkitem:checkItems){
            ValidatorResponse valitem=validateItem(checkitem,user);
            if(!valitem.isValid)
                return valitem;
        }
        return new ValidatorResponse(true,null);
    }

    public static ValidatorResponse validateSearch(Users user,String name){
        if(user==null)
            return new ValidatorResponse(false,new InvalidAuthenticationProblem());
        if(name==null || name==""){
            return new ValidatorResponse(false,new InvalidParameterProblem("name","name cant be empty or null"));

        }
        return new ValidatorResponse(true,null);
    }
}
