package com.isel.daw.checklist.controllers;

import com.isel.daw.checklist.RequiresAuthentication;
import com.isel.daw.checklist.model.RequestsDTO.UserRequestDTO;
import com.isel.daw.checklist.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService=userService;
    }

    @PostMapping(path="/create", produces="application/json")
    public ResponseEntity<?> createUser(@RequestBody UserRequestDTO user) {
        return userService.create(user);
    }
/*
    @PostMapping(path="/login", produces="application/json")
    @RequiresAuthentication
    public ResponseEntity<?> login(@RequestHeader(value="Authorization")String authorization) {
        return userService.login(authorization);
    }
*/
}
