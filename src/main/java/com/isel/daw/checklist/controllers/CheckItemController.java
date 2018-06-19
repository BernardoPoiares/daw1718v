package com.isel.daw.checklist.controllers;

import com.isel.daw.checklist.RequiresAuthentication;
import com.isel.daw.checklist.model.CheckItem;
import com.isel.daw.checklist.model.RequestsDTO.CheckItemRequestDto;
import com.isel.daw.checklist.model.Users;
import com.isel.daw.checklist.services.CheckItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/checkItem")
public class CheckItemController {

    private final CheckItemService itemService;

    @Autowired
    public CheckItemController(CheckItemService itemService){
        this.itemService=itemService;
    }

    @GetMapping(path="/{id}", produces="application/vnd.siren+json")
    @RequiresAuthentication
    public ResponseEntity<?> getById(@RequestHeader(value="Authorization")String authorization,@PathVariable("id") long id) {

        return itemService.getItemById(authorization,id);
    }


    @PostMapping(path="/create", produces={"application/json","application/problem+json"})
    @RequiresAuthentication
    public ResponseEntity<?> create(@RequestHeader(value="Authorization")String authorization, @RequestBody CheckItemRequestDto checkItemRequestDto) {
        return itemService.create(authorization,checkItemRequestDto);
    }


}
