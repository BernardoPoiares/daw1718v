package com.isel.daw.checklist.model.Validators;

import com.isel.daw.checklist.ValidatorResponse;
import com.isel.daw.checklist.model.DataBaseDTOs.Users;
import com.isel.daw.checklist.model.RequestsDTO.UserRequestDTO;
import com.isel.daw.checklist.problems.InvalidAuthenticationProblem;
import com.isel.daw.checklist.problems.NotFoundProblem;

public class UserValidator{

        public static ValidatorResponse validateUser(Users user, UserRequestDTO userreq_dto){
            if(user==null)
                return new ValidatorResponse(false,new NotFoundProblem("There is no user with that username"));
            if(!user.getUserName().equals(userreq_dto.getUsername()) || !user.getPassword().equals(userreq_dto.getPassword()))
                return new ValidatorResponse(false,new InvalidAuthenticationProblem());
            return new ValidatorResponse(true,null);
        }
}
