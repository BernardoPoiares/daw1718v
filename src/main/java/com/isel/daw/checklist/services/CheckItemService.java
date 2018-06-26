package com.isel.daw.checklist.services;

import com.isel.daw.checklist.ServiceResponse;
import com.isel.daw.checklist.ValidatorResponse;
import com.isel.daw.checklist.model.*;
import com.isel.daw.checklist.Service;
import com.isel.daw.checklist.model.DataBaseDTOs.CheckItem;
import com.isel.daw.checklist.model.DataBaseDTOs.CheckItemTemplate;
import com.isel.daw.checklist.model.DataBaseDTOs.Users;
import com.isel.daw.checklist.model.RequestsDTO.CheckItemRequestDto;
import com.isel.daw.checklist.model.SirenBuilders.CheckItemSirenBuilder;
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

@Component
public class CheckItemService implements Service {

    private final CheckItemRepository itemRepository;
    private final CheckItemTemplateRepository itemTemplateRepository;
    private final UserRepository userRepository;

    @Autowired
    public CheckItemService(CheckItemRepository itemRepository, UserRepository userRepository, CheckItemTemplateRepository itemTemplateService){
        this.itemRepository=itemRepository;
        this.userRepository=userRepository;
        this.itemTemplateRepository=itemTemplateService;
    }

    public ServiceResponse<CheckItem> getItemById(String authorization,long id){
        Users user=userRepository.findByToken(authorization.split(" ")[1]);
        ValidatorResponse valtUser= CheckItemValidator.validateUser(user);
        if(!valtUser.isValid)
            return new ServiceResponse<>(null,valtUser.problem);
        CheckItem checkItem= itemRepository.findById(id);
        ValidatorResponse valtcheckItem=CheckItemValidator.validateItemById(checkItem,id,user);
       if(!valtcheckItem.isValid)
           return new ServiceResponse<>(null,valtcheckItem.problem);
       return new ServiceResponse<>(checkItem,null);
    }

    @Transactional
    public ServiceResponse<CheckItem> create(String authorization, CheckItemRequestDto checkitem_dto){
        Users user=userRepository.findByToken(authorization.split(" ")[1]);
        ValidatorResponse valtUser=CheckItemValidator.validateUser(user);
        if(!valtUser.isValid)
            return new ServiceResponse<>(null,valtUser.problem);
        ValidatorResponse valtcheckItem=CheckItemValidator.validateItemCreateRequest(checkitem_dto);
        if(!valtcheckItem.isValid)
            return new ServiceResponse<>(null,valtcheckItem.problem);
        CheckItemTemplate newitemTemplate=new CheckItemTemplate(checkitem_dto.getName(),checkitem_dto.getDescription(),user);
        CheckItemTemplate saveditemtemplate=itemTemplateRepository.save(newitemTemplate);
        if(saveditemtemplate==null)
            return new ServiceResponse<>(null,new InternalServerProblem());
        CheckItem newitem=new CheckItem("uncompleted",saveditemtemplate);
        CheckItem savedcheckitem=itemRepository.save(newitem);
        if(savedcheckitem==null) {
            itemTemplateRepository.deleteById(saveditemtemplate.getId());
            return new ServiceResponse<>(null, new InternalServerProblem());
        }
        return new ServiceResponse<>(savedcheckitem,null);
    }

    @Transactional
    public ServiceResponse<CheckItem> update(String authorization, CheckItemRequestDto checkitem_dto){
        Users user=userRepository.findByToken(authorization.split(" ")[1]);
        ValidatorResponse valtUser=CheckItemValidator.validateUser(user);
        if(!valtUser.isValid)
            return new ServiceResponse<>(null,valtUser.problem);
        ValidatorResponse valtcheckItem=CheckItemValidator.validateItemUpdateRequest(checkitem_dto);
        if(!valtcheckItem.isValid)
            return new ServiceResponse<>(null,valtcheckItem.problem);
        CheckItem checkitem= itemRepository.findById(checkitem_dto.getId());
        if(checkitem==null)
            return new ServiceResponse<>(null,new InternalServerProblem());
        if(checkitem_dto.getState()!=null)
            checkitem.setState(checkitem_dto.getState());
        if(checkitem_dto.getName()!=null || checkitem_dto.getDescription()!=null){
            CheckItemTemplate itemtemplatetosave=checkitem.getCheckitem_itemtemplate();
            long numbTempuses=itemRepository.countByTemplateId(checkitem.getCheckitem_itemtemplate().getId());
            if(numbTempuses>1) {
                itemtemplatetosave = new CheckItemTemplate(itemtemplatetosave.getName(), itemtemplatetosave.getDescription(), itemtemplatetosave.getItemTemplate_user()); // has to create a new one because is use by more items
                itemtemplatetosave=itemTemplateRepository.save(itemtemplatetosave);
                if(itemtemplatetosave==null){
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); //set rollback
                    return new ServiceResponse<>(null,new InternalServerProblem());
                }
            }
            if(checkitem_dto.getName()!=null)
                itemtemplatetosave.setName(checkitem_dto.getName());
            if(checkitem_dto.getDescription()!=null)
                itemtemplatetosave.setDescription(checkitem_dto.getDescription());
            checkitem.setCheckitem_itemtemplate(itemtemplatetosave);
        }
        return new ServiceResponse<>(checkitem,null);

    }


    @Transactional
    public ServiceResponse<CheckItem> delete(String authorization, long id){
        Users user=userRepository.findByToken(authorization.split(" ")[1]);
        ValidatorResponse valtUser=CheckItemValidator.validateUser(user);
        if(!valtUser.isValid)
            return new ServiceResponse<>(null,valtUser.problem);
        CheckItem checkItem= itemRepository.findById(id);
        ValidatorResponse valtcheckItem=CheckItemValidator.validateItemById(checkItem,id,user);
        if(!valtcheckItem.isValid)
            return new ServiceResponse<>(null,valtcheckItem.problem);
        long delt_item_res= itemRepository.deleteById(checkItem.getId());
        if(delt_item_res==0)
            return new ServiceResponse<>(null,new InternalServerProblem());
        long numbTempuses=itemRepository.countByTemplateId(checkItem.getCheckitem_itemtemplate().getId());
        if(numbTempuses==0) {
            long delt_itemtemp_res=itemTemplateRepository.deleteById(checkItem.getCheckitem_itemtemplate().getId());
            if(delt_itemtemp_res==0){
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); //set rollback
                return new ServiceResponse<>(null,new InternalServerProblem());
            }
        }
        //todo:change response
        return new ServiceResponse<>(checkItem,null);
    }

}
