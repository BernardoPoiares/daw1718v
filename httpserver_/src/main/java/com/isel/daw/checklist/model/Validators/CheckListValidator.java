package com.isel.daw.checklist.model.Validators;

import com.isel.daw.checklist.ValidatorResponse;
import com.isel.daw.checklist.model.DataBaseDTOs.CheckList;
import com.isel.daw.checklist.model.RequestsDTO.CheckListRequestDto;
import com.isel.daw.checklist.model.DataBaseDTOs.Users;
import com.isel.daw.checklist.problems.*;

public class CheckListValidator {

    public static ValidatorResponse validateUser(Users user){
        if(user==null)
            return new ValidatorResponse(false,new InvalidAuthenticationProblem());
        return new ValidatorResponse(true,null);
    }
    public static ValidatorResponse valrequestCklById(Users user, long id) {
        if (user == null)
            return new ValidatorResponse(false, new InvalidAuthenticationProblem());
        if (id < 1)
            return new ValidatorResponse(false, new InvalidParameterProblem("id", "'id' must be bigger then 0."));
        return new ValidatorResponse(true, null);
    }

    public static ValidatorResponse valCkl_User(CheckList checklist,Users user,long id){
        if(checklist==null ||checklist.getList_user().getId()!=user.getId()) {
            return new ValidatorResponse(false, new NotFoundProblem("CheckList with the id '" + id + "'"));
        }return new ValidatorResponse(true,null);
    }

    public static ValidatorResponse valUpdateRequest(Users user,CheckListRequestDto checklist_dto){
        if(user==null)
            return new ValidatorResponse(false,new InvalidAuthenticationProblem());
        if(checklist_dto==null)
            return new ValidatorResponse(false,new BadRequestProblem("checklist", "The object 'checklist' must be not empty."));
        if(checklist_dto.getId()<1)
            return new ValidatorResponse(false,new InvalidParameterProblem("id","'id' must be bigger then 0."));
        if(checklist_dto.getCompletionDate()==null && checklist_dto.getName()==null)
            return new ValidatorResponse(false,new InvalidMultiParameterProblem("name,completionDate"));
        return new ValidatorResponse(true,null);
    }
    public static ValidatorResponse valUser_Ckldto(Users user,CheckListRequestDto checklist_dto){
        if (user == null)
            return new ValidatorResponse(false, new InvalidAuthenticationProblem());
        if(checklist_dto==null)
            return new ValidatorResponse(false,new BadRequestProblem("checklist", "The object 'checklist' must be not empty."));
        if(checklist_dto.getCompletionDate()==null || checklist_dto.getName()==null)
            return new ValidatorResponse(false,new InvalidMultiParameterProblem("name;completionDate"));
        return new ValidatorResponse(true,null);
    }

    public static ValidatorResponse valLists(CheckList[] checkLists,Users user){
        for(CheckList checkList:checkLists){
            ValidatorResponse vallist = valCkl_User(checkList,user,checkList.getId());
            if(!vallist.isValid)
                return vallist;
        }
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
