package com.isel.daw.checklist.controllers;


import com.isel.daw.checklist.model.CheckList;
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

    @GetMapping(path="/{id}", produces="application/json")
    public ResponseEntity<?> getById(@PathVariable("id") int id) {
        return checkListService.getById(id);
    }

    @PostMapping(path="/create", produces="application/json")
    public ResponseEntity<?> create(@PathVariable("name") String name,@PathVariable("completiondate") Date completiondate){
        return checkListService.create(name,completiondate);
    }
}