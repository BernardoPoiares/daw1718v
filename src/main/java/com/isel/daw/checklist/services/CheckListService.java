package com.isel.daw.checklist.services;

import com.isel.daw.checklist.ValidatorResponse;
import com.isel.daw.checklist.model.*;
import com.isel.daw.checklist.model.RequestsDTO.CheckItemRequestDto;
import com.isel.daw.checklist.model.RequestsDTO.CheckListRequestDto;
import com.isel.daw.checklist.model.SirenBuilders.CheckItemSirenBuilder;
import com.isel.daw.checklist.model.SirenBuilders.CheckListSirenBuilder;
import com.isel.daw.checklist.problems.InternalServerProblem;
import com.isel.daw.checklist.repositories.*;
import org.hibernate.exception.DataException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Component
public class CheckListService {

    private final CheckListRepository checkListRepository;
    private final CheckListTemplateRepository listTemplateRepository;
    private final UserRepository userRepository;

    @Autowired
    public CheckListService(CheckListRepository listRepository, UserRepository userRepository, CheckListTemplateRepository listTemplateService){
        this.checkListRepository=listRepository;
        this.userRepository=userRepository;
        this.listTemplateRepository=listTemplateService;
    }
    public ResponseEntity<?> getById(int id){
        CheckList res=checkListRepository.findById(id);
        return new ResponseEntity<>(
                res,
                HttpStatus.ACCEPTED
        );
    }
/*
    public ResponseEntity<?> getListById(String authorization,long id){
        Users user=userRepository.findByToken(authorization.split(" ")[1]);
        ValidatorResponse valtUser= CheckItemValidator.validateUser(user);
        if(!valtUser.isValid)
            return ResponseBuilder.buildError(valtUser.problem);
        CheckItem checkItem= itemRepository.findById(id);
        ValidatorResponse valtcheckItem=CheckItemValidator.validateItemById(checkItem,id,user);
        if(!valtcheckItem.isValid)
            return ResponseBuilder.buildError(valtcheckItem.problem);
        return ResponseBuilder.build(
                CheckItemSirenBuilder.build(checkItem.getId(),
                        checkItem.getCheckitem_itemtemplate().getName(),
                        checkItem.getCheckitem_itemtemplate().getDescription(),
                        checkItem.getState())
        );
    }*/


    public ResponseEntity<?> create(String authorization, CheckListRequestDto checklist_dto){
        Users user=userRepository.findByToken(authorization.split(" ")[1]);
        ValidatorResponse valtUser=CheckListValidator.validateUser(user);
        if(!valtUser.isValid)
            return ResponseBuilder.buildError(valtUser.problem);
        CheckList newlist=new CheckList(checklist_dto.getName(),user,checklist_dto.getCompletionDate());
        CheckList savedchecklist=checkListRepository.save(newlist);
        if(savedchecklist==null)
            return ResponseBuilder.buildError(new InternalServerProblem());
        return ResponseBuilder.build(
                CheckListSirenBuilder.build(savedchecklist.getId(),
                        savedchecklist.getName(),
                        savedchecklist.getCompletionDate())
        );
    }

    @Transactional
    public ResponseEntity<?> update(String authorization, CheckListRequestDto checklist_dto){
        Users user=userRepository.findByToken(authorization.split(" ")[1]);
        ValidatorResponse valtUser=CheckItemValidator.validateUser(user);
        if(!valtUser.isValid)
            return ResponseBuilder.buildError(valtUser.problem);
        ValidatorResponse valtcheckList=CheckListValidator.validateListRequest(checklist_dto);
        if(!valtcheckList.isValid)
            return ResponseBuilder.buildError(valtcheckList.problem);
        CheckList checklist= checkListRepository.findById(checklist_dto.getId());
        if(checklist_dto.getName()!=null)
            checklist.setName(checklist_dto.getName());
        if(checklist_dto.getCompletionDate()!=null)
            checklist.setCompletionDate(checklist_dto.getCompletionDate());
        CheckList updatedchecklist=checkListRepository.save(checklist);
        if(updatedchecklist==null)
            return ResponseBuilder.buildError(new InternalServerProblem());
        return ResponseBuilder.build(
                CheckListSirenBuilder.build(updatedchecklist.getId(),
                        updatedchecklist.getName(),
                        updatedchecklist.getCompletionDate())
        );
    }

/*
    @Transactional
    public ResponseEntity<?> delete(String authorization, long id){
        Users user=userRepository.findByToken(authorization.split(" ")[1]);
        ValidatorResponse valtUser=CheckItemValidator.validateUser(user);
        if(!valtUser.isValid)
            return ResponseBuilder.buildError(valtUser.problem);
        CheckItem checkItem= itemRepository.findById(id);
        ValidatorResponse valtcheckItem=CheckItemValidator.validateItemById(checkItem,id,user);
        if(!valtcheckItem.isValid)
            return ResponseBuilder.buildError(valtcheckItem.problem);
        long delt_item_res= itemRepository.deleteById(checkItem.getId());
        if(delt_item_res==0)
            return ResponseBuilder.buildError(new InternalServerProblem());         //todo:check transactional better
        long numbTempuses=itemRepository.countByTemplateId(checkItem.getCheckitem_itemtemplate().getId());
        if(numbTempuses==0)
            itemTemplateRepository.deleteById(checkItem.getCheckitem_itemtemplate().getId());
        //todo:change response
        return ResponseBuilder.build(
                CheckItemSirenBuilder.build(checkItem.getId(),
                        checkItem.getCheckitem_itemtemplate().getName(),
                        checkItem.getCheckitem_itemtemplate().getDescription(),
                        checkItem.getState())
        );
    }*/
}
