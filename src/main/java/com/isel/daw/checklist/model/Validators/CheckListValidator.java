package com.isel.daw.checklist.model.Validators;

import com.isel.daw.checklist.ValidatorResponse;
import com.isel.daw.checklist.model.DataBaseDTOs.CheckList;
import com.isel.daw.checklist.model.RequestsDTO.CheckListRequestDto;
import com.isel.daw.checklist.model.DataBaseDTOs.Users;
import com.isel.daw.checklist.problems.InvalidAuthenticationProblem;
import com.isel.daw.checklist.problems.InvalidMultiParameterProblem;
import com.isel.daw.checklist.problems.InvalidParameterProblem;
import com.isel.daw.checklist.problems.NotFoundProblem;

public class CheckListValidator {

    public static ValidatorResponse validateUser(Users user){
        if(user==null)
            return new ValidatorResponse(false,new InvalidAuthenticationProblem());
        return new ValidatorResponse(true,null);
    }


    public static ValidatorResponse validateListById(CheckList checklist, long id, Users user){
        if(user==null)
            return new ValidatorResponse(false,new InvalidAuthenticationProblem());
        if(checklist==null || !(checklist.getList_user().getUserName().equals(user.getUserName()))) {
            if(id<1)
                return new ValidatorResponse(false,new InvalidParameterProblem("id","'id' must be bigger then 0."));
            return new ValidatorResponse(false, new NotFoundProblem("CheckList with the id '" + id + "'"));
        }return new ValidatorResponse(true,null);
    }

    public static ValidatorResponse validateListRequest(CheckListRequestDto checklist_dto){
        if(checklist_dto==null)
            return new ValidatorResponse(false,new InvalidAuthenticationProblem());
        if(checklist_dto.getId()<1)
            return new ValidatorResponse(false,new InvalidParameterProblem("id","'id' must be bigger then 0."));
        if(checklist_dto.getCompletionDate()==null && checklist_dto.getName()==null)
            return new ValidatorResponse(false,new InvalidMultiParameterProblem("name,completionDate"));
        return new ValidatorResponse(true,null);
    }
/*
    public static ValidatorResponse validateListAddCheckItemsRequest(CheckListRequestDto checklist_dto){
        if(checklist_dto==null)
            return new ValidatorResponse(false,new InvalidAuthenticationProblem());
        if(checklist_dto.getId()<1)
            return new ValidatorResponse(false,new InvalidParameterProblem("id","'id' must be bigger then 0."));
        if(checklist_dto.getCheckitems()==null)
            return new ValidatorResponse(false,new InvalidParameterProblem("checkitems","'checkitems' must be have items."));
        return new ValidatorResponse(true,null);
    }
*/
}
