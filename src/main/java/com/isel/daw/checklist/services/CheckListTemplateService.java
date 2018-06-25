package com.isel.daw.checklist.services;

import com.isel.daw.checklist.ValidatorResponse;
import com.isel.daw.checklist.model.*;
import com.isel.daw.checklist.model.RequestsDTO.CheckItemRequestDto;
import com.isel.daw.checklist.model.RequestsDTO.CheckListTemplateRequestDto;
import com.isel.daw.checklist.model.SirenBuilders.CheckListTemplateSirenBuilder;
import com.isel.daw.checklist.problems.InternalServerProblem;
import com.isel.daw.checklist.repositories.CheckItemTemplateRepository;
import com.isel.daw.checklist.repositories.CheckListTemplateRepository;
import com.isel.daw.checklist.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.transaction.Transactional;

@Component
public class CheckListTemplateService {
    private final CheckListTemplateRepository listTemplateRepository;
    private final CheckItemTemplateRepository itemTemplateRepository;
    private final UserRepository userRepository;

    @Autowired
    public CheckListTemplateService(UserRepository userRepository, CheckListTemplateRepository listTemplateRepository,CheckItemTemplateRepository itemTemplateRepository){
        this.userRepository=userRepository;
        this.listTemplateRepository=listTemplateRepository;
        this.itemTemplateRepository=itemTemplateRepository;
    }

    public ResponseEntity<?> getListById(String authorization, long id){
        Users user=userRepository.findByToken(authorization.split(" ")[1]);
        ValidatorResponse valtUser= CheckListTemplateValidator.validateUser(user);
        if(!valtUser.isValid)
            return ResponseBuilder.buildError(valtUser.problem);
        CheckListTemplate checklisttemplate= listTemplateRepository.findById(id);
        if(checklisttemplate==null)
            return ResponseBuilder.buildError(new InternalServerProblem());
        ValidatorResponse valtcheckList=CheckListTemplateValidator.validateListTemplateById(checklisttemplate,id,user);
        if(!valtcheckList.isValid)
            return ResponseBuilder.buildError(valtcheckList.problem);
        return ResponseBuilder.build(
                CheckListTemplateSirenBuilder.build(checklisttemplate.getId(),
                        checklisttemplate.getName())
        );
    }


    public ResponseEntity<?> create(String authorization, CheckListTemplateRequestDto checklisttemplate_dto){
        Users user=userRepository.findByToken(authorization.split(" ")[1]);
        ValidatorResponse valtUser=CheckListTemplateValidator.validateUser(user);
        if(!valtUser.isValid)
            return ResponseBuilder.buildError(valtUser.problem);
        CheckListTemplate newlisttemplate=new CheckListTemplate(checklisttemplate_dto.getName(),user);
        CheckListTemplate savedchecklisttemplate=listTemplateRepository.save(newlisttemplate);
        if(savedchecklisttemplate==null)
            return ResponseBuilder.buildError(new InternalServerProblem());
        return ResponseBuilder.build(
                CheckListTemplateSirenBuilder.build(savedchecklisttemplate.getId(),
                        savedchecklisttemplate.getName())
        );
    }

    @Transactional
    public ResponseEntity<?> update(String authorization, CheckListTemplateRequestDto checklisttemplate_dto){
        Users user=userRepository.findByToken(authorization.split(" ")[1]);
        ValidatorResponse valtUser= CheckListTemplateValidator.validateUser(user);
        if(!valtUser.isValid)
            return ResponseBuilder.buildError(valtUser.problem);
        ValidatorResponse valtcheckList=CheckListTemplateValidator.validateListTemplateRequest(checklisttemplate_dto);
        if(!valtcheckList.isValid)
            return ResponseBuilder.buildError(valtcheckList.problem);
        CheckListTemplate checklisttemplate= listTemplateRepository.findById(checklisttemplate_dto.getId());
        if(checklisttemplate==null)
            return ResponseBuilder.buildError(new InternalServerProblem());
        checklisttemplate.setName(checklisttemplate_dto.getName());
        return ResponseBuilder.build(
                CheckListTemplateSirenBuilder.build(checklisttemplate.getId(),
                        checklisttemplate.getName())
        );
    }


    @Transactional
    public ResponseEntity<?> delete(String authorization, long id){
        Users user=userRepository.findByToken(authorization.split(" ")[1]);
        ValidatorResponse valtUser=CheckListTemplateValidator.validateUser(user);
        if(!valtUser.isValid)
            return ResponseBuilder.buildError(valtUser.problem);
        CheckListTemplate checklisttemplate= listTemplateRepository.findById(id);
        if(checklisttemplate==null)
            return ResponseBuilder.buildError(new InternalServerProblem());
        ValidatorResponse valtcheckList=CheckListTemplateValidator.validateListTemplateById(checklisttemplate,id,user);
        if(!valtcheckList.isValid)
            return ResponseBuilder.buildError(valtcheckList.problem);
        long delt_list_res= listTemplateRepository.deleteById(id);
        if(delt_list_res==0)
            return ResponseBuilder.buildError(new InternalServerProblem()); //todo: remove itens?
        //todo:change response
        return ResponseBuilder.build(
                CheckListTemplateSirenBuilder.build(checklisttemplate.getId(),
                        checklisttemplate.getName())
        );
    }

    @Transactional
    public ResponseEntity<?> addCheckItemsTemplate(String authorization, CheckListTemplateRequestDto checklisttemplate_dto){
        Users user=userRepository.findByToken(authorization.split(" ")[1]);
        ValidatorResponse valtUser=CheckListTemplateValidator.validateUser(user);
        if(!valtUser.isValid)
            return ResponseBuilder.buildError(valtUser.problem);
        ValidatorResponse valtcheckList=CheckListTemplateValidator.validateListTemplatetoAddItemsRequest(checklisttemplate_dto);
        if(!valtUser.isValid)
            return ResponseBuilder.buildError(valtcheckList.problem);
        CheckListTemplate checklisttemplate= listTemplateRepository.findById(checklisttemplate_dto.getId());
        if(checklisttemplate==null)
            return ResponseBuilder.buildError(new InternalServerProblem());
        for (CheckItemRequestDto checkitem_dto:checklisttemplate_dto.getCheckitems()) {
            ValidatorResponse valtcheckitem=CheckItemValidator.validateItemRequest(checkitem_dto);
            if(!valtUser.isValid) {
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); //set rollback
                return ResponseBuilder.buildError(valtcheckList.problem);
            }
            CheckItemTemplate checkitem_saved=itemTemplateRepository.save(new CheckItemTemplate(checkitem_dto.getName(),checkitem_dto.getDescription(),user));
            if(checkitem_saved==null){
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); //set rollback
                return ResponseBuilder.buildError(valtcheckList.problem);
            }
            checklisttemplate.addItemsTemplates(checkitem_saved);
        }
        return ResponseBuilder.build(
                CheckListTemplateSirenBuilder.build(checklisttemplate.getId(),
                        checklisttemplate.getName())
        );
    }

}
