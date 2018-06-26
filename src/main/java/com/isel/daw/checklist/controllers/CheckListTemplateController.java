package com.isel.daw.checklist.controllers;

import com.isel.daw.checklist.model.RequestsDTO.CheckListTemplateRequestDto;
import com.isel.daw.checklist.services.CheckListTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/checkListTemplate")
public class CheckListTemplateController {


    private final CheckListTemplateService checkListTemplateService;

    @Autowired
    public CheckListTemplateController(CheckListTemplateService checkListTemplateService) {
        this.checkListTemplateService = checkListTemplateService;
    }

    @GetMapping(path = "/{id}", produces = {"application/vnd.siren+json", "application/problem+json"})
    public ResponseEntity<?> getListById(@RequestHeader(value = "Authorization") String authorization, @PathVariable("id") long id) {
        return checkListTemplateService.getListById(authorization, id);
    }

    @PostMapping(path = "/create", produces = {"application/vnd.siren+json", "application/problem+json"})
    public ResponseEntity<?> create(@RequestHeader(value = "Authorization") String authorization, @RequestBody CheckListTemplateRequestDto checklisttemplate_ReqDto) {
        return checkListTemplateService.create(authorization, checklisttemplate_ReqDto);
    }

    @PostMapping(path = "/update", produces = {"application/vnd.siren+json", "application/problem+json"})
    public ResponseEntity<?> update(@RequestHeader(value = "Authorization") String authorization, @RequestBody CheckListTemplateRequestDto checklisttemplate_ReqDto) {
        return checkListTemplateService.update(authorization, checklisttemplate_ReqDto);
    }

    @PostMapping(path = "/addCheckItems", produces = {"application/vnd.siren+json", "application/problem+json"})
    public ResponseEntity<?> addCheckItemsTemplates(@RequestHeader(value = "Authorization") String authorization, @RequestBody CheckListTemplateRequestDto checklisttemplate_ReqDto) {
        return checkListTemplateService.addCheckItemsTemplate(authorization, checklisttemplate_ReqDto);
    }

    @DeleteMapping(path = "/{id}", produces = {"application/vnd.siren+json", "application/problem+json"})
    public ResponseEntity<?> delete(@RequestHeader(value = "Authorization") String authorization, @PathVariable("id") long id) {
        return checkListTemplateService.delete(authorization, id);
    }

    @DeleteMapping(path = "/checkItemsTemplates", produces = {"application/vnd.siren+json", "application/problem+json"})
    public ResponseEntity<?> deleteitemsTemplates(@RequestHeader(value = "Authorization") String authorization, @RequestBody CheckListTemplateRequestDto checklisttemplate_ReqDto) {
        return checkListTemplateService.deleteCheckItemsTemplate(authorization, checklisttemplate_ReqDto);
    }
}