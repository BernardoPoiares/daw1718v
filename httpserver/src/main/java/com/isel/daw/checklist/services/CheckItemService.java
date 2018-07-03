package com.isel.daw.checklist.services;

import com.isel.daw.checklist.ServiceResponse;
import com.isel.daw.checklist.ValidatorResponse;
import com.isel.daw.checklist.model.*;
import com.isel.daw.checklist.Service;
import com.isel.daw.checklist.model.DataBaseDTOs.CheckItem;
import com.isel.daw.checklist.model.DataBaseDTOs.CheckItemTemplate;
import com.isel.daw.checklist.model.DataBaseDTOs.Users;
import com.isel.daw.checklist.model.RequestsDTO.CheckItemRequestDto;
import com.isel.daw.checklist.model.RequestsDTO.CheckItemTemplateRequestDto;
import com.isel.daw.checklist.model.SirenBuilders.CheckItemSirenBuilder;
import com.isel.daw.checklist.model.Validators.CheckItemTemplateValidator;
import com.isel.daw.checklist.model.Validators.CheckItemValidator;
import com.isel.daw.checklist.problems.InternalServerProblem;
import com.isel.daw.checklist.repositories.CheckItemRepository;
import com.isel.daw.checklist.repositories.CheckItemTemplateRepository;
import com.isel.daw.checklist.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.transaction.Transactional;
import javax.xml.validation.Validator;

@Component
public class CheckItemService implements Service {

    private final CheckItemRepository itemRepository;
    private final CheckItemTemplateRepository itemTemplateRepository;
    private final UserRepository userRepository;
    private final CheckItemTemplateService checkItemTemplateService;

    @Autowired
    public CheckItemService(CheckItemRepository itemRepository, UserRepository userRepository, CheckItemTemplateRepository itemTemplateService,CheckItemTemplateService checkItemTemplateService){
        this.itemRepository=itemRepository;
        this.userRepository=userRepository;
        this.itemTemplateRepository=itemTemplateService;
        this.checkItemTemplateService=checkItemTemplateService;
    }

    public ServiceResponse<CheckItem> getItemById(String authorization,long id){
        Users user=userRepository.findByToken(authorization.split(" ")[1]);
        ValidatorResponse valtUser_id= CheckItemValidator.validateUser_Id(user,id);
        if(!valtUser_id.isValid)
            return new ServiceResponse<>(null,valtUser_id.problem);
        CheckItem checkItem= itemRepository.findById(id);
        ValidatorResponse valtcheckItem=CheckItemValidator.validateItem(checkItem,user);
       if(!valtcheckItem.isValid)
           return new ServiceResponse<>(null,valtcheckItem.problem);
       return new ServiceResponse<>(checkItem,null);
    }


    public ServiceResponse<CheckItem> createByTemplate(CheckItemTemplate checkitemtemplate){
        CheckItem newitem=new CheckItem("uncompleted",checkitemtemplate);
        CheckItem savedcheckitem=itemRepository.save(newitem);
        if(savedcheckitem==null)
            return new ServiceResponse<>(null, new InternalServerProblem());
        return new ServiceResponse<>(savedcheckitem,null);
    }

}