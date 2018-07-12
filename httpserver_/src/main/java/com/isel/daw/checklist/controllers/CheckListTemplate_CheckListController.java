package com.isel.daw.checklist.controllers;

import com.isel.daw.checklist.RequiresAuthentication;
import com.isel.daw.checklist.ServiceResponse;
import com.isel.daw.checklist.model.DataBaseDTOs.CheckList;
import com.isel.daw.checklist.model.RequestsDTO.CheckItem_CheckListRequestDto;
import com.isel.daw.checklist.model.RequestsDTO.CheckListTemplate_CheckListRequestDto;
import com.isel.daw.checklist.model.ResponseBuilder;
import com.isel.daw.checklist.model.SirenBuilders.CheckListSirenBuilder;
import com.isel.daw.checklist.services.CheckItem_CheckListService;
import com.isel.daw.checklist.services.CheckListTemplate_CheckListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/checkListTemplate_checkList")

public class CheckListTemplate_CheckListController {


    private final CheckListTemplate_CheckListService checkListTemplate_CheckListService;

    @Autowired
    public CheckListTemplate_CheckListController(CheckListTemplate_CheckListService checkListTemplate_CheckListService) {
        this.checkListTemplate_CheckListService = checkListTemplate_CheckListService;
    }

    @PostMapping(path="/createCheckList", produces= {
            "application/vnd.siren+json", "application/problem+json"
    })
    @RequiresAuthentication
    public ResponseEntity<?> createListfromListTemplate(@RequestHeader(value = "Authorization") String authorization, @RequestBody CheckListTemplate_CheckListRequestDto checklisttemplate_checkListRequestDto) {
        ServiceResponse<?> response = checkListTemplate_CheckListService.createListfromListTemplate(authorization, checklisttemplate_checkListRequestDto);
        if (response.getError() != null)
            return ResponseBuilder.buildError(response.getError());
        CheckList checkList = (CheckList)response.getResponse();
        return ResponseBuilder.build(
                CheckListSirenBuilder.build(checkList.getId(),
                        checkList.getName(),
                        checkList.getCompletionDate()));
    }

/*
    @DeleteMapping(path="/deleteCheckItems", produces= {
            "application/vnd.siren+json", "application/problem+json"
    })
    @RequiresAuthentication
    public ResponseEntity<?> deleteCheckItemsfromCheckList(@RequestHeader(value = "Authorization") String authorization, @RequestBody CheckItem_CheckListRequestDto checkItem_checkListRequestDto) {
        ServiceResponse<?> response = checkItem_checkListService.deleteCheckItems(authorization, checkItem_checkListRequestDto);
        if (response.getError() != null)
            return ResponseBuilder.buildError(response.getError());
        CheckList checkList = (CheckList)response.getResponse();
        return ResponseBuilder.build(
                CheckListSirenBuilder.build(checkList.getId(),
                        checkList.getName(),
                        checkList.getCompletionDate()));
    }*/
}
