package com.isel.daw.checklist.controllers;

import com.isel.daw.checklist.ServiceResponse;
import com.isel.daw.checklist.model.DataBaseDTOs.CheckList;
import com.isel.daw.checklist.model.DataBaseDTOs.CheckListTemplate;
import com.isel.daw.checklist.model.RequestsDTO.CheckListTemplateRequestDto;
import com.isel.daw.checklist.model.ResponseBuilder;
import com.isel.daw.checklist.model.SirenBuilders.CheckListTemplateSirenBuilder;
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
        ServiceResponse<CheckListTemplate> response=checkListTemplateService.getListById(authorization, id);
        if(response.getError()!=null)
            return ResponseBuilder.buildError(response.getError());
        CheckListTemplate checklisttemplate=response.getResponse();
        return ResponseBuilder.build(
                CheckListTemplateSirenBuilder.build(checklisttemplate.getId(),
                        checklisttemplate.getName())
        );
    }

    @PostMapping(path = "/create", produces = {"application/vnd.siren+json", "application/problem+json"})
    public ResponseEntity<?> create(@RequestHeader(value = "Authorization") String authorization, @RequestBody CheckListTemplateRequestDto checklisttemplate_ReqDto) {
        ServiceResponse<CheckListTemplate> response=checkListTemplateService.create(authorization, checklisttemplate_ReqDto);
        if(response.getError()!=null)
            return ResponseBuilder.buildError(response.getError());
        CheckListTemplate checklisttemplate=response.getResponse();
        return ResponseBuilder.build(
                CheckListTemplateSirenBuilder.build(checklisttemplate.getId(),
                        checklisttemplate.getName())
        );
    }

    @PostMapping(path = "/update", produces = {"application/vnd.siren+json", "application/problem+json"})
    public ResponseEntity<?> update(@RequestHeader(value = "Authorization") String authorization, @RequestBody CheckListTemplateRequestDto checklisttemplate_ReqDto) {
        ServiceResponse<CheckListTemplate> response=checkListTemplateService.update(authorization, checklisttemplate_ReqDto);
        if(response.getError()!=null)
            return ResponseBuilder.buildError(response.getError());
        CheckListTemplate checklisttemplate=response.getResponse();
        return ResponseBuilder.build(
                CheckListTemplateSirenBuilder.build(checklisttemplate.getId(),
                        checklisttemplate.getName())
        );
    }

    @PostMapping(path = "/addCheckItems", produces = {"application/vnd.siren+json", "application/problem+json"})
    public ResponseEntity<?> addCheckItemsTemplates(@RequestHeader(value = "Authorization") String authorization, @RequestBody CheckListTemplateRequestDto checklisttemplate_ReqDto) {
        ServiceResponse<CheckListTemplate> response=checkListTemplateService.addCheckItemsTemplate(authorization, checklisttemplate_ReqDto);
        if(response.getError()!=null)
            return ResponseBuilder.buildError(response.getError());
        CheckListTemplate checklisttemplate=response.getResponse();
        return ResponseBuilder.build(
                CheckListTemplateSirenBuilder.build(checklisttemplate.getId(),
                        checklisttemplate.getName())
        );
    }

    @DeleteMapping(path = "/{id}", produces = {"application/vnd.siren+json", "application/problem+json"})
    public ResponseEntity<?> delete(@RequestHeader(value = "Authorization") String authorization, @PathVariable("id") long id) {
        ServiceResponse<CheckListTemplate> response=checkListTemplateService.delete(authorization, id);
        if(response.getError()!=null)
            return ResponseBuilder.buildError(response.getError());
        CheckListTemplate checklisttemplate=response.getResponse();
        return ResponseBuilder.build(
                CheckListTemplateSirenBuilder.build(checklisttemplate.getId(),
                        checklisttemplate.getName())
        );
    }

    @DeleteMapping(path = "/checkItemsTemplates", produces = {"application/vnd.siren+json", "application/problem+json"})
    public ResponseEntity<?> deleteitemsTemplates(@RequestHeader(value = "Authorization") String authorization, @RequestBody CheckListTemplateRequestDto checklisttemplate_ReqDto) {
        ServiceResponse<CheckListTemplate> response=checkListTemplateService.deleteCheckItemsTemplate(authorization, checklisttemplate_ReqDto);
        if(response.getError()!=null)
            return ResponseBuilder.buildError(response.getError());
        CheckListTemplate checklisttemplate=response.getResponse();
        return ResponseBuilder.build(
                CheckListTemplateSirenBuilder.build(checklisttemplate.getId(),
                        checklisttemplate.getName())
        );
    }
}