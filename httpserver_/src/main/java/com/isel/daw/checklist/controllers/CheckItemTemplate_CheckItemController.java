package com.isel.daw.checklist.controllers;

import com.isel.daw.checklist.RequiresAuthentication;
import com.isel.daw.checklist.ServiceResponse;
import com.isel.daw.checklist.model.DataBaseDTOs.CheckItem;
import com.isel.daw.checklist.model.RequestsDTO.CheckItemRequestDto;
import com.isel.daw.checklist.model.ResponseBuilder;
import com.isel.daw.checklist.model.SirenBuilders.CheckItemSirenBuilder;
import com.isel.daw.checklist.services.CheckItemService;
import com.isel.daw.checklist.services.CheckItemTemplate_CheckItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/checkItemTemplate_checkitem")

public class CheckItemTemplate_CheckItemController {

    private final CheckItemTemplate_CheckItemService itemtemplate_itemService;

    @Autowired
    public CheckItemTemplate_CheckItemController(CheckItemTemplate_CheckItemService itemtemplate_itemService){
        this.itemtemplate_itemService=itemtemplate_itemService;
    }


        @PostMapping(path="/create", produces={"application/vnd.siren+json","application/problem+json"})
    @RequiresAuthentication
    public ResponseEntity<?> create(@RequestHeader(value="Authorization")String authorization, @RequestBody CheckItemRequestDto checkItemRequestDto) {
        ServiceResponse<CheckItem> response=itemtemplate_itemService.create(authorization,checkItemRequestDto);
        if(response.getError()!=null)
            return ResponseBuilder.buildError(response.getError());
        CheckItem checkItem=response.getResponse();
        return ResponseBuilder.build(
                CheckItemSirenBuilder.build(checkItem.getId(),
                        checkItem.getCheckitem_itemtemplate().getName(),
                        checkItem.getCheckitem_itemtemplate().getDescription(),
                        checkItem.getState())
        );
    }

    @PostMapping(path="/update", produces={"application/vnd.siren+json","application/problem+json"})
    @RequiresAuthentication
    public ResponseEntity<?> update(@RequestHeader(value="Authorization")String authorization, @RequestBody CheckItemRequestDto checkItemRequestDto) {
        ServiceResponse<?> response=itemtemplate_itemService.update(authorization,checkItemRequestDto);
        if(response.getError()!=null)
            return ResponseBuilder.buildError(response.getError());
        CheckItem checkItem=(CheckItem)response.getResponse();
        return ResponseBuilder.build(
                CheckItemSirenBuilder.build(checkItem.getId(),
                        checkItem.getCheckitem_itemtemplate().getName(),
                        checkItem.getCheckitem_itemtemplate().getDescription(),
                        checkItem.getState())
        );
    }

    @DeleteMapping(path="/{id}", produces={"application/vnd.siren+json","application/problem+json"})
    @RequiresAuthentication
    public ResponseEntity<?> delete(@RequestHeader(value="Authorization")String authorization, @PathVariable("id") long id) {
        ServiceResponse<CheckItem> response=itemtemplate_itemService.delete(authorization,id);
        if(response.getError()!=null)
            return ResponseBuilder.buildError(response.getError());
        CheckItem checkItem=response.getResponse();
        return ResponseBuilder.build(
                CheckItemSirenBuilder.build(checkItem.getId(),
                        checkItem.getCheckitem_itemtemplate().getName(),
                        checkItem.getCheckitem_itemtemplate().getDescription(),
                        checkItem.getState())
        );
    }
}
