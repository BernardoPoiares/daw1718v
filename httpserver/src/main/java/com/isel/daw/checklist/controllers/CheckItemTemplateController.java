package com.isel.daw.checklist.controllers;

import com.isel.daw.checklist.RequiresAuthentication;
import com.isel.daw.checklist.services.CheckItemTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/checkItemTemplate")
public class CheckItemTemplateController {

    private final CheckItemTemplateService itemTemplateService;

    @Autowired
    public CheckItemTemplateController(CheckItemTemplateService itemTemplateService) {
        this.itemTemplateService = itemTemplateService;
    }

}

