package com.isel.daw.checklist.controllers;

import com.isel.daw.checklist.RequiresAuthentication;
import com.isel.daw.checklist.ServiceResponse;
import com.isel.daw.checklist.model.DataBaseDTOs.Users;
import com.isel.daw.checklist.model.RequestsDTO.UserRequestDTO;
import com.isel.daw.checklist.model.ResponseBuilder;
import com.isel.daw.checklist.model.SirenBuilders.CheckListTemplateSirenBuilder;
import com.isel.daw.checklist.model.SirenBuilders.UserSirenBuilder;
import com.isel.daw.checklist.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:9000", maxAge = 3600)
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService=userService;
    }

    @PostMapping(path="/create", produces="application/json")
    public ResponseEntity<?> createUser(@RequestBody UserRequestDTO userrequest_dto) {
        ServiceResponse<Users> response= userService.create(userrequest_dto);
        if(response.getError()!=null)
            return ResponseBuilder.buildError(response.getError());
        Users user=response.getResponse();
        return ResponseBuilder.build(   UserSirenBuilder.build(user.getId(),user.getUserName(),user.getToken()));
    }


    @PostMapping(path="/login", produces="application/json")
    @RequiresAuthentication
    public ResponseEntity<?> login(@RequestBody UserRequestDTO userrequest_dto) {
        ServiceResponse<Users> response= userService.login(userrequest_dto);
        if(response.getError()!=null)
            return ResponseBuilder.buildError(response.getError());
        Users user=response.getResponse();
        return ResponseBuilder.build(   UserSirenBuilder.build(user.getId(),user.getUserName(),user.getToken()));
    }

}
