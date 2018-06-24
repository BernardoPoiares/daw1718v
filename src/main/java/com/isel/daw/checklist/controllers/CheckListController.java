package com.isel.daw.checklist.controllers;


import com.isel.daw.checklist.model.CheckList;
import com.isel.daw.checklist.model.RequestsDTO.CheckItemRequestDto;
import com.isel.daw.checklist.model.RequestsDTO.CheckListRequestDto;
import com.isel.daw.checklist.services.CheckListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/checkList")
public class CheckListController {


    private final CheckListService checkListService;

    @Autowired
    public CheckListController(CheckListService checkListService){
        this.checkListService=checkListService;
    }

    @GetMapping(path="/{id}", produces={"application/vnd.siren+json","application/problem+json"})
    public ResponseEntity<?> getListById(@RequestHeader(value="Authorization") String authorization,@PathVariable("id") long id) {
        return checkListService.getListById(authorization,id);
    }

    @PostMapping(path="/create", produces={"application/vnd.siren+json","application/problem+json"})
    public ResponseEntity<?> create(@RequestHeader(value="Authorization")String authorization, @RequestBody CheckListRequestDto checklistRequestDto){
        return checkListService.create(authorization,checklistRequestDto);
    }

    @PostMapping(path="/update", produces={"application/vnd.siren+json","application/problem+json"})
    public ResponseEntity<?> update(@RequestHeader(value="Authorization")String authorization, @RequestBody CheckListRequestDto checklistRequestDto){
        return checkListService.update(authorization,checklistRequestDto);
    }

    @DeleteMapping(path="/{id}", produces={"application/vnd.siren+json","application/problem+json"})
    public ResponseEntity<?> delete(@RequestHeader(value="Authorization")String authorization, @PathVariable("id") long id){
        return checkListService.delete(authorization,id);
    }

}