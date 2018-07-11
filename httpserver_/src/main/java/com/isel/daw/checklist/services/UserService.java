package com.isel.daw.checklist.services;

import com.isel.daw.checklist.Service;
import com.isel.daw.checklist.ServiceResponse;
import com.isel.daw.checklist.ValidatorResponse;
import com.isel.daw.checklist.model.RequestsDTO.UserRequestDTO;
import com.isel.daw.checklist.model.Validators.UserValidator;
import com.isel.daw.checklist.problems.ConflictProblem;
import com.isel.daw.checklist.problems.InternalServerProblem;
import com.isel.daw.checklist.problems.InvalidParameterProblem;
import com.isel.daw.checklist.problems.NotFoundProblem;
import com.isel.daw.checklist.repositories.UserRepository;
import com.isel.daw.checklist.model.DataBaseDTOs.Users;
import com.isel.daw.checklist.utils.ValidatorObj;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.List;

@Component
public class UserService implements Service {
    public final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository){
        this.userRepository=userRepository;
    }

    public ResponseEntity<?> getUser(String username){
        List<Users> res=userRepository.findAll();
        return new ResponseEntity<>(res.size(),
                HttpStatus.ACCEPTED
        );

    }

    public ServiceResponse<Users> create(UserRequestDTO userdto){
        Users user=userRepository.findByUsername(userdto.getUsername());
        if(user!=null)
            return new ServiceResponse<>(null,new ConflictProblem("There is already a User with the username given."));
        Users newuser=new Users(userdto.getUsername(), userdto.getPassword(),Base64.getEncoder().encodeToString((userdto.getUsername()+":"+userdto.getPassword()).getBytes()));
        Users user_res=userRepository.save(newuser);
        if(user_res==null)
            return new ServiceResponse<>(null,new InternalServerProblem());
        return new ServiceResponse<>(user_res,null);
    }

    public ServiceResponse<Users> login(UserRequestDTO userdto){
        Users user=userRepository.findByUsername(userdto.getUsername());
        ValidatorResponse valuser= UserValidator.validateUser(user,userdto);
        if(!valuser.isValid)
            return new ServiceResponse<>(null,valuser.problem);
        return new ServiceResponse<>(user,null);
    }

}
