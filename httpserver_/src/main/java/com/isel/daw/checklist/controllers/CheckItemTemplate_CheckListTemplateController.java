package com.isel.daw.checklist.controllers;

import com.isel.daw.checklist.Converter;
import com.isel.daw.checklist.RequiresAuthentication;
import com.isel.daw.checklist.ServiceResponse;
import com.isel.daw.checklist.model.DataBaseDTOs.CheckItemTemplate;
import com.isel.daw.checklist.model.DataBaseDTOs.CheckListTemplate;
import com.isel.daw.checklist.model.RequestsDTO.CheckItemTemplateRequestDto;
import com.isel.daw.checklist.model.RequestsDTO.CheckListTemplateRequestDto;
import com.isel.daw.checklist.model.ResponseBuilder;
import com.isel.daw.checklist.model.SirenBuilders.CheckItemTemplateArraySirenBuilder;
import com.isel.daw.checklist.model.SirenBuilders.CheckItemTemplateSirenBuilder;
import com.isel.daw.checklist.model.SirenBuilders.CheckListTemplateArraySirenBuilder;
import com.isel.daw.checklist.model.SirenBuilders.CheckListTemplateSirenBuilder;
import com.isel.daw.checklist.services.CheckItemTemplate_CheckListTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/checkItemTemplate_CheckListTemplate")

public class CheckItemTemplate_CheckListTemplateController {


    private final CheckItemTemplate_CheckListTemplateService checkItemTemplate_CheckListTemplateService;

    @Autowired
    public CheckItemTemplate_CheckListTemplateController(CheckItemTemplate_CheckListTemplateService checkItemTemplate_CheckListTemplateService) {
        this.checkItemTemplate_CheckListTemplateService = checkItemTemplate_CheckListTemplateService;
    }

    @PostMapping(path="/createAndAdd", produces= {
            "application/vnd.siren+json", "application/problem+json"
    })
    @RequiresAuthentication
    public ResponseEntity<?> createAndAdd(@RequestHeader(value = "Authorization") String authorization, @RequestBody CheckListTemplateRequestDto checklisttemplateRequestDto) {
        ServiceResponse<?> serv_resp=checkItemTemplate_CheckListTemplateService.createAndAdd(authorization,checklisttemplateRequestDto);
        if(serv_resp.getError()!=null)
            return ResponseBuilder.buildError(serv_resp.getError());
        return ResponseBuilder.build(
                CheckItemTemplateSirenBuilder.build(Converter.CheckItemTemplateDto_CheckItemTemplate((CheckItemTemplate) serv_resp.getResponse()))
        );
    }


    @GetMapping(path="/getByList/{id}", produces= {
            "application/vnd.siren+json", "application/problem+json"
    })
    @RequiresAuthentication
    public ResponseEntity<?> getByList(@RequestHeader(value = "Authorization") String authorization, @PathVariable("id") long checklisttemp_id) {
        ServiceResponse<?> serv_resp=checkItemTemplate_CheckListTemplateService.getByList(authorization,checklisttemp_id);
        if(serv_resp.getError()!=null)
            return ResponseBuilder.buildError(serv_resp.getError());
        return ResponseBuilder.build(
                CheckItemTemplateArraySirenBuilder.build((CheckItemTemplateRequestDto[]) serv_resp.getResponse()));
    }

    @DeleteMapping(path = "/{id}", produces = {"application/vnd.siren+json", "application/problem+json"})
    public ResponseEntity<?> delete(@RequestHeader(value = "Authorization") String authorization, @PathVariable("id") long id) {
        ServiceResponse<CheckListTemplate> response=checkItemTemplate_CheckListTemplateService.delete(authorization, id);
        if(response.getError()!=null)
            return ResponseBuilder.buildError(response.getError());
        CheckListTemplate checklisttemplate=response.getResponse();
        return ResponseBuilder.build(
                CheckListTemplateSirenBuilder.build(checklisttemplate.getId(),
                        checklisttemplate.getName())
        );
    }


    @DeleteMapping(path = "/various", produces = {"application/vnd.siren+json", "application/problem+json"})
    public ResponseEntity<?> deleteVarious(@RequestHeader(value = "Authorization") String authorization, @RequestBody CheckListTemplateRequestDto[] checklisttemplates_ReqDto) {
        ServiceResponse<CheckListTemplateRequestDto[]> response=checkItemTemplate_CheckListTemplateService.deleteVarious(authorization, checklisttemplates_ReqDto);
        if(response.getError()!=null)
            return ResponseBuilder.buildError(response.getError());
        CheckListTemplateRequestDto[] checklisttemplates=response.getResponse();
        return ResponseBuilder.build(
                CheckListTemplateArraySirenBuilder.build(checklisttemplates)
        );
    }
}