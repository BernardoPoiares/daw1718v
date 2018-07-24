package com.isel.daw.checklist.controllers;

import com.isel.daw.checklist.Converter;
import com.isel.daw.checklist.RequiresAuthentication;
import com.isel.daw.checklist.ServiceResponse;
import com.isel.daw.checklist.model.DataBaseDTOs.CheckItem;
import com.isel.daw.checklist.model.DataBaseDTOs.CheckList;
import com.isel.daw.checklist.model.RequestsDTO.CheckItem_CheckListRequestDto;
import com.isel.daw.checklist.model.RequestsDTO.CheckItem_CheckListsRequestDto;
import com.isel.daw.checklist.model.RequestsDTO.CheckItem_CheckListsRequestDto;
import com.isel.daw.checklist.model.RequestsDTO.CheckListRequestDto;
import com.isel.daw.checklist.model.ResponseBuilder;
import com.isel.daw.checklist.model.SirenBuilders.CheckItemSirenBuilder;
import com.isel.daw.checklist.model.SirenBuilders.CheckListArraySirenBuilder;
import com.isel.daw.checklist.model.SirenBuilders.CheckListSirenBuilder;
import com.isel.daw.checklist.services.CheckItem_CheckListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/checkItem_CheckList")

public class CheckItem_CheckListController {


    private final CheckItem_CheckListService checkItem_checkListService;

    @Autowired
    public CheckItem_CheckListController(CheckItem_CheckListService checkItem_checkListService) {
        this.checkItem_checkListService = checkItem_checkListService;
    }

    @PostMapping(path="/addCheckItems", produces= {
        "application/vnd.siren+json", "application/problem+json"
    })
    @RequiresAuthentication
    public ResponseEntity<?> addCheckItemstoCheckList(@RequestHeader(value = "Authorization") String authorization, @RequestBody CheckItem_CheckListRequestDto checkItem_checkListsRequestDto) {
        ServiceResponse<?> response = checkItem_checkListService.addCheckItems(authorization, checkItem_checkListsRequestDto);
        if (response.getError() != null)
            return ResponseBuilder.buildError(response.getError());
        CheckList checkList = (CheckList)response.getResponse();
        return ResponseBuilder.build(
                CheckListSirenBuilder.build(checkList.getId(),
                        checkList.getName(),
                        checkList.getCompletionDate()));
    }


    @DeleteMapping(path="/deleteCheckItems", produces= {
            "application/vnd.siren+json", "application/problem+json"
    })
    @RequiresAuthentication
    public ResponseEntity<?> deleteCheckItemsfromCheckList(@RequestHeader(value = "Authorization") String authorization, @RequestBody CheckItem_CheckListRequestDto checkItem_checkListRequestDto) {
        ServiceResponse<?> response = checkItem_checkListService.deleteCheckItems(authorization, checkItem_checkListRequestDto);
        if (response.getError() != null)
            return ResponseBuilder.buildError(response.getError());
        return ResponseBuilder.build(
                CheckListSirenBuilder.build(Converter.CheckListDTO_CheckList((CheckList)response.getResponse())));
    }

    @PostMapping(path="/createAndAdd", produces= {
            "application/vnd.siren+json", "application/problem+json"
    })
    @RequiresAuthentication
    public ResponseEntity<?> createAndAdd(@RequestHeader(value = "Authorization") String authorization, @RequestBody CheckItem_CheckListRequestDto checkItem_checkListRequestDto) {
        ServiceResponse<?> response = checkItem_checkListService.createAndAdd(authorization, checkItem_checkListRequestDto);
        if (response.getError() != null)
            return ResponseBuilder.buildError(response.getError());
        return ResponseBuilder.build(
                CheckItemSirenBuilder.build(Converter.CheckItemDto_CheckItem((CheckItem)response.getResponse())));
    }


    @GetMapping(path="/searchByItem", produces={"application/vnd.siren+json","application/problem+json"})
    @RequiresAuthentication
    public ResponseEntity<?> searchByList(@RequestHeader(value="Authorization")String authorization,@RequestParam("id") long itemid ) {
        ServiceResponse<?> response=checkItem_checkListService.searchByItem(authorization,itemid);
        if(response.getError()!=null)
            return ResponseBuilder.buildError(response.getError());
        return ResponseBuilder.build(
                CheckListArraySirenBuilder.build(((CheckListRequestDto[])response.getResponse()))
        );
    }


    @DeleteMapping(path="/itemFromLists", produces= {
            "application/vnd.siren+json", "application/problem+json"
    })
    @RequiresAuthentication
    public ResponseEntity<?> removeItemFromLists(@RequestHeader(value = "Authorization") String authorization, @RequestBody CheckItem_CheckListsRequestDto checkItem_checkListsRequestDto) {
        ServiceResponse<?> response = checkItem_checkListService.removeItemFromLists(authorization, checkItem_checkListsRequestDto);
        if (response.getError() != null)
            return ResponseBuilder.buildError(response.getError());
        return ResponseBuilder.build(
                CheckItemSirenBuilder.build(Converter.CheckItemDto_CheckItem((CheckItem)response.getResponse())));
    }
}