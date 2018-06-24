package com.isel.daw.checklist.model;

import com.isel.daw.checklist.ValidatorResponse;
import com.isel.daw.checklist.model.RequestsDTO.CheckListRequestDto;
import com.isel.daw.checklist.problems.InvalidAuthenticationProblem;
import com.isel.daw.checklist.problems.InvalidMultiParameterProblem;
import com.isel.daw.checklist.problems.InvalidParameterProblem;

public class CheckListValidator {

    public static ValidatorResponse validateUser(Users user){
        if(user==null)
            return new ValidatorResponse(false,new InvalidAuthenticationProblem());
        return new ValidatorResponse(true,null);
    }

    public static ValidatorResponse validateListRequest(CheckListRequestDto checklist_dto){
        if(checklist_dto==null)
            return new ValidatorResponse(false,new InvalidAuthenticationProblem());
        if(checklist_dto.getId()<1)
            return new ValidatorResponse(false,new InvalidParameterProblem("id","'id' must be bigger then 0."));
        if(checklist_dto.getCompletionDate()==null && checklist_dto.getName()==null)
            return new ValidatorResponse(false,new InvalidMultiParameterProblem(new String []{"name","completionDate"}));
        return new ValidatorResponse(true,null);
    }

}
