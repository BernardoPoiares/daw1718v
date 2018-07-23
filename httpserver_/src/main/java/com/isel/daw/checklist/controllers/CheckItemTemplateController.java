package com.isel.daw.checklist.controllers;

import com.isel.daw.checklist.RequiresAuthentication;
import com.isel.daw.checklist.ServiceResponse;
import com.isel.daw.checklist.model.RequestsDTO.CheckItemTemplateRequestDto;
import com.isel.daw.checklist.model.ResponseBuilder;
import com.isel.daw.checklist.model.SirenBuilders.CheckItemTemplateArraySirenBuilder;
import com.isel.daw.checklist.services.CheckItemTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/checkItemTemplate")

public class CheckItemTemplateController {

    private final CheckItemTemplateService checkItemTemplateService;

    @Autowired
    public CheckItemTemplateController(CheckItemTemplateService checkItemTemplateService) {
        this.checkItemTemplateService = checkItemTemplateService;
    }


    @DeleteMapping(path = "/various", produces = {"application/vnd.siren+json", "application/problem+json"})
    public ResponseEntity<?> deleteVarious(@RequestHeader(value = "Authorization") String authorization, @RequestBody CheckItemTemplateRequestDto[] checklisttemplates_ReqDto) {
        ServiceResponse<?> response=checkItemTemplateService.deleteVarious(authorization, checklisttemplates_ReqDto);
        if(response.getError()!=null)
            return ResponseBuilder.buildError(response.getError());
        return ResponseBuilder.build(
                CheckItemTemplateArraySirenBuilder.build((CheckItemTemplateRequestDto[]) response.getResponse())
        );
    }
}

