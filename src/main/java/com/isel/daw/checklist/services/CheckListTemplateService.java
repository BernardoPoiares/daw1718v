package com.isel.daw.checklist.services;

import com.isel.daw.checklist.ServiceResponse;
import com.isel.daw.checklist.ValidatorResponse;
import com.isel.daw.checklist.model.*;
import com.isel.daw.checklist.model.DataBaseDTOs.CheckItemTemplate;
import com.isel.daw.checklist.model.DataBaseDTOs.CheckListTemplate;
import com.isel.daw.checklist.model.DataBaseDTOs.Users;
import com.isel.daw.checklist.model.RequestsDTO.CheckItemRequestDto;
import com.isel.daw.checklist.model.RequestsDTO.CheckItemTemplateRequestDto;
import com.isel.daw.checklist.model.RequestsDTO.CheckListTemplateRequestDto;
import com.isel.daw.checklist.model.SirenBuilders.CheckListTemplateSirenBuilder;
import com.isel.daw.checklist.model.Validators.CheckItemTemplateValidator;
import com.isel.daw.checklist.model.Validators.CheckItemValidator;
import com.isel.daw.checklist.model.Validators.CheckListTemplateValidator;
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

    @Transactional
    public ServiceResponse<CheckListTemplate> getListById(String authorization, long id){
        Users user=userRepository.findByToken(authorization.split(" ")[1]);
        ValidatorResponse valtUser= CheckListTemplateValidator.validateUser(user);
        if(!valtUser.isValid)
            return new ServiceResponse<>(null,valtUser.problem);
        CheckListTemplate checklisttemplate= listTemplateRepository.findById(id);
        if(checklisttemplate==null)
            return new ServiceResponse<>(null,new InternalServerProblem());
        ValidatorResponse valtcheckList=CheckListTemplateValidator.validateListTemplateById(checklisttemplate,id,user);
        if(!valtcheckList.isValid)
            return new ServiceResponse<>(null,valtcheckList.problem);
        return new ServiceResponse<>(checklisttemplate,null);
    }


    public ServiceResponse<CheckListTemplate> create(String authorization, CheckListTemplateRequestDto checklisttemplate_dto){
        Users user=userRepository.findByToken(authorization.split(" ")[1]);
        ValidatorResponse valtUser=CheckListTemplateValidator.validateUser(user);
        if(!valtUser.isValid)
            return new ServiceResponse<>(null,valtUser.problem);
        CheckListTemplate newlisttemplate=new CheckListTemplate(checklisttemplate_dto.getName(),user);
        CheckListTemplate savedchecklisttemplate=listTemplateRepository.save(newlisttemplate);
        if(savedchecklisttemplate==null)
            return new ServiceResponse<>(null,new InternalServerProblem());
        return new ServiceResponse<>(savedchecklisttemplate,null);
    }

    @Transactional
    public ServiceResponse<CheckListTemplate> update(String authorization, CheckListTemplateRequestDto checklisttemplate_dto){
        Users user=userRepository.findByToken(authorization.split(" ")[1]);
        ValidatorResponse valtUser= CheckListTemplateValidator.validateUser(user);
        if(!valtUser.isValid)
            return new ServiceResponse<>(null,valtUser.problem);
        ValidatorResponse valtcheckList=CheckListTemplateValidator.validateListTemplateRequest(checklisttemplate_dto);
        if(!valtcheckList.isValid)
            return new ServiceResponse<>(null,valtcheckList.problem);
        CheckListTemplate checklisttemplate= listTemplateRepository.findById(checklisttemplate_dto.getId());
        if(checklisttemplate==null)
            return new ServiceResponse<>(null,new InternalServerProblem());
        checklisttemplate.setName(checklisttemplate_dto.getName());
        return new ServiceResponse<>(checklisttemplate,null);
    }


    @Transactional
    public ServiceResponse<CheckListTemplate> delete(String authorization, long id){
        Users user=userRepository.findByToken(authorization.split(" ")[1]);
        ValidatorResponse valtUser=CheckListTemplateValidator.validateUser(user);
        if(!valtUser.isValid)
            return new ServiceResponse<>(null,valtUser.problem);
        CheckListTemplate checklisttemplate= listTemplateRepository.findById(id);
        if(checklisttemplate==null)
            return new ServiceResponse<>(null,new InternalServerProblem());
        ValidatorResponse valtcheckList=CheckListTemplateValidator.validateListTemplateById(checklisttemplate,id,user);
        if(!valtcheckList.isValid)
            return new ServiceResponse<>(null,valtcheckList.problem);
        long delt_list_res= listTemplateRepository.deleteById(id);
        if(delt_list_res==0)
            return new ServiceResponse<>(null,new InternalServerProblem()); //todo: remove itens?
        //todo:change response
        return new ServiceResponse<>(checklisttemplate,null);
    }

    @Transactional
    public ServiceResponse<CheckListTemplate> addCheckItemsTemplate(String authorization, CheckListTemplateRequestDto checklisttemplate_dto){
        Users user=userRepository.findByToken(authorization.split(" ")[1]);
        ValidatorResponse valtUser=CheckListTemplateValidator.validateUser(user);
        if(!valtUser.isValid)
            return new ServiceResponse<>(null,valtUser.problem);
        ValidatorResponse valtcheckList=CheckListTemplateValidator.validateListTemplatetoAddItemsRequest(checklisttemplate_dto);
        if(!valtUser.isValid)
            return new ServiceResponse<>(null,valtcheckList.problem);
        CheckListTemplate checklisttemplate= listTemplateRepository.findById(checklisttemplate_dto.getId());
        if(checklisttemplate==null)
            return new ServiceResponse<>(null,new InternalServerProblem());
        for (CheckItemTemplateRequestDto checkitemtemplate_dto:checklisttemplate_dto.getCheckitemstemplates()) {
            ValidatorResponse valtcheckitem= CheckItemTemplateValidator.validateCreateRequest(checkitemtemplate_dto,user);
            if(!valtcheckitem.isValid) {
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); //set rollback
                return new ServiceResponse<>(null,valtcheckList.problem);
            }
            CheckItemTemplate checkitem_saved=itemTemplateRepository.save(new CheckItemTemplate(checkitemtemplate_dto.getName(),checkitemtemplate_dto.getDescription(),checklisttemplate,user));
            if(checkitem_saved==null){
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); //set rollback
                return new ServiceResponse<>(null,new InternalServerProblem());
            }
        }
        return new ServiceResponse<>(checklisttemplate,null);

    }


    @Transactional
    public ServiceResponse<CheckListTemplate> deleteCheckItemsTemplate(String authorization, CheckListTemplateRequestDto checklisttemplate_dto){
        Users user=userRepository.findByToken(authorization.split(" ")[1]);
        ValidatorResponse valtUser=CheckListTemplateValidator.validateUser(user);
        if(!valtUser.isValid)
            return new ServiceResponse<>(null,valtUser.problem);
        ValidatorResponse valtcheckList=CheckListTemplateValidator.validateListTemplatetoDeleteItemsRequest(checklisttemplate_dto);
        if(!valtUser.isValid)
            return new ServiceResponse<>(null,valtcheckList.problem);
        CheckListTemplate checklisttemplate= listTemplateRepository.findById(checklisttemplate_dto.getId());
        if(checklisttemplate==null)
            return new ServiceResponse<>(null,new InternalServerProblem());
        for (CheckItemTemplateRequestDto checkitemtemplate_dto:checklisttemplate_dto.getCheckitemstemplates()) { //todo:create itemtemplaterequestdto
            CheckItemTemplate checkItemTemplate= itemTemplateRepository.getById(checkitemtemplate_dto.getId());
            ValidatorResponse valtcheckitem= CheckItemTemplateValidator.validateDeltReqTempList(checkItemTemplate,user,checklisttemplate);
            if(!valtcheckitem.isValid) {
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); //set rollback
                return new ServiceResponse<>(null,valtcheckList.problem);
            }
            long del_res=itemTemplateRepository.deleteById(checkItemTemplate.getId());
            if(del_res==0){
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); //set rollback
                return new ServiceResponse<>(null,new InternalServerProblem());
            }
        }
        return new ServiceResponse<>(checklisttemplate,null);
    }
}
