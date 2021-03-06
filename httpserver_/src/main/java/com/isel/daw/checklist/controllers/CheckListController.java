package com.isel.daw.checklist.controllers;


import com.isel.daw.checklist.Converter;
import com.isel.daw.checklist.RequiresAuthentication;
import com.isel.daw.checklist.Service;
import com.isel.daw.checklist.ServiceResponse;
import com.isel.daw.checklist.model.DataBaseDTOs.CheckList;
import com.isel.daw.checklist.model.RequestsDTO.CheckListRequestDto;
import com.isel.daw.checklist.model.ResponseBuilder;
import com.isel.daw.checklist.model.SirenBuilders.CheckListArraySirenBuilder;
import com.isel.daw.checklist.model.SirenBuilders.CheckListSirenBuilder;
import com.isel.daw.checklist.services.CheckListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/checkList")

public class CheckListController {


    private final CheckListService checkListService;

    @Autowired
    public CheckListController(CheckListService checkListService){
        this.checkListService=checkListService;
    }

    @GetMapping(path="/{id}", produces={"application/vnd.siren+json","application/problem+json"})
    @RequiresAuthentication
    public ResponseEntity<?> getListById(@RequestHeader(value="Authorization") String authorization,@PathVariable("id") long id) {
        ServiceResponse<CheckList> response=checkListService.getListById(authorization,id);
        if(response.getError()!=null)
            return ResponseBuilder.buildError(response.getError());
        CheckList checklist=response.getResponse();
        return ResponseBuilder.build(
                CheckListSirenBuilder.build(checklist.getId(),
                        checklist.getName(),
                        checklist.getCompletionDate())
        );
    }

    @GetMapping(path="/all", produces={"application/vnd.siren+json","application/problem+json"})
    @RequiresAuthentication
    public ResponseEntity<?> getAllByUser(@RequestHeader(value="Authorization") String authorization) {
        ServiceResponse<CheckListRequestDto[]> response=checkListService.getAll(authorization);
        if(response.getError()!=null)
            return ResponseBuilder.buildError(response.getError());
        return ResponseBuilder.build(
                CheckListArraySirenBuilder.build(response.getResponse())
        );
    }

    @PostMapping(path="/create", produces={"application/vnd.siren+json","application/problem+json"})
    @RequiresAuthentication
    public ResponseEntity<?> create(@RequestHeader(value="Authorization")String authorization, @RequestBody CheckListRequestDto checklistRequestDto){
        ServiceResponse<CheckList> response=checkListService.create(authorization,checklistRequestDto);
        if(response.getError()!=null)
            return ResponseBuilder.buildError(response.getError());
        CheckList checklist=response.getResponse();
        return ResponseBuilder.build(
                CheckListSirenBuilder.build(Converter.CheckListDTO_CheckList(response.getResponse()))
        );
    }

    @PostMapping(path="/update", produces={"application/vnd.siren+json","application/problem+json"})
    public ResponseEntity<?> update(@RequestHeader(value="Authorization")String authorization, @RequestBody CheckListRequestDto checklistRequestDto){
        ServiceResponse<CheckList> response=checkListService.update(authorization,checklistRequestDto);
        if(response.getError()!=null)
            return ResponseBuilder.buildError(response.getError());
        CheckList checklist=response.getResponse();
        return ResponseBuilder.build(
                CheckListSirenBuilder.build(checklist.getId(),
                        checklist.getName(),
                        checklist.getCompletionDate())
        );
    }

    @DeleteMapping(path="/{id}", produces={"application/vnd.siren+json","application/problem+json"})
    public ResponseEntity<?> delete(@RequestHeader(value="Authorization")String authorization, @PathVariable("id") long id){
        ServiceResponse<CheckList> response=checkListService.delete(authorization,id);
        if(response.getError()!=null)
            return ResponseBuilder.buildError(response.getError());
        CheckList checklist=response.getResponse();
        return ResponseBuilder.build(
                CheckListSirenBuilder.build(checklist.getId(),
                        checklist.getName(),
                        checklist.getCompletionDate())
        );
    }

    @DeleteMapping(path="/various", produces={"application/vnd.siren+json","application/problem+json"})
    public ResponseEntity<?> delete(@RequestHeader(value="Authorization")String authorization,@RequestBody CheckListRequestDto[] checklists_dto){
        ServiceResponse<CheckListRequestDto[]> response=checkListService.deleteVarious(authorization,checklists_dto);
        if(response.getError()!=null)
            return ResponseBuilder.buildError(response.getError());
        return ResponseBuilder.build(
                CheckListArraySirenBuilder.build(response.getResponse())
        );
    }

    @GetMapping(path="/search", produces={"application/vnd.siren+json","application/problem+json"})
    @RequiresAuthentication
    public ResponseEntity<?> searchByName(@RequestHeader(value="Authorization")String authorization,@RequestParam("name") String name ) {
        ServiceResponse<CheckListRequestDto[]> response=checkListService.searchByName(authorization,name);
        if(response.getError()!=null)
            return ResponseBuilder.buildError(response.getError());
        return ResponseBuilder.build(
                CheckListArraySirenBuilder.build(response.getResponse())
        );
    }


}