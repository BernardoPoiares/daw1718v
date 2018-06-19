package com.isel.daw.checklist.model;

import com.isel.daw.checklist.ValidatorResponse;
import com.isel.daw.checklist.model.RequestsDTO.CheckItemRequestDto;
import com.isel.daw.checklist.problems.InvalidAuthenticationProblem;
import com.isel.daw.checklist.problems.InvalidMultiParameterProblem;
import com.isel.daw.checklist.problems.InvalidParameterProblem;
import com.isel.daw.checklist.problems.NotFoundProblem;
import org.springframework.http.HttpStatus;

import java.lang.Error;

public class CheckItemValidator {

    public static ValidatorResponse validateItemById(CheckItem checkItem, long id,Users user){
        if(user==null)
            return new ValidatorResponse(false,new InvalidAuthenticationProblem());
        if(checkItem==null || !(checkItem.getCheckitem_itemtemplate().getItemTemplate_user().getUserName().equals(user.getUserName()))) {
            if(id<1)
                return new ValidatorResponse(false,new InvalidParameterProblem("id","'id' must be bigger then 0."));
            return new ValidatorResponse(false, new NotFoundProblem("CheckItem with the id '" + id + "'"));
        }return new ValidatorResponse(true,null);
    }

    public static ValidatorResponse validateUser(Users user){
        if(user==null)
            return new ValidatorResponse(false,new InvalidAuthenticationProblem());
        return new ValidatorResponse(true,null);
    }

    public static ValidatorResponse validateItemRequest(CheckItemRequestDto checkitem_dto){
        if(checkitem_dto==null)
            return new ValidatorResponse(false,new InvalidAuthenticationProblem());
        if(checkitem_dto.getId()<1)
            return new ValidatorResponse(false,new InvalidParameterProblem("id","'id' must be bigger then 0."));
        if(checkitem_dto.getDescription()==null && checkitem_dto.getName()==null && checkitem_dto.getState()==null)
            return new ValidatorResponse(false,new InvalidMultiParameterProblem(new String []{"description","name","state"}));
        return new ValidatorResponse(true,null);
    }


}
