package com.isel.daw.checklist.services;

import com.isel.daw.checklist.ValidatorResponse;
import com.isel.daw.checklist.model.*;
import com.isel.daw.checklist.Service;
import com.isel.daw.checklist.model.RequestsDTO.CheckItemRequestDto;
import com.isel.daw.checklist.model.SirenBuilders.CheckItemSirenBuilder;
import com.isel.daw.checklist.problems.InternalServerProblem;
import com.isel.daw.checklist.problems.InvalidAuthenticationProblem;
import com.isel.daw.checklist.problems.InvalidParameterProblem;
import com.isel.daw.checklist.repositories.CheckItemRepository;
import com.isel.daw.checklist.repositories.CheckItemTemplateRepository;
import com.isel.daw.checklist.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    public ResponseEntity<?> getItemById(String authorization,long id){
        Users user=userRepository.findByToken(authorization.split(" ")[1]);
        ValidatorResponse valtUser=CheckItemValidator.validateUser(user);
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
    }

    @Transactional
    public ResponseEntity<?> create(String authorization, CheckItemRequestDto checkitem_dto){
        Users user=userRepository.findByToken(authorization.split(" ")[1]);
        ValidatorResponse valtUser=CheckItemValidator.validateUser(user);
        if(!valtUser.isValid)
            return ResponseBuilder.buildError(valtUser.problem);
        ValidatorResponse valtcheckItem=CheckItemValidator.validateItemCreateRequest(checkitem_dto);
        if(!valtcheckItem.isValid)
            return ResponseBuilder.buildError(valtcheckItem.problem);
        CheckItemTemplate newitemTemplate=new CheckItemTemplate(checkitem_dto.getName(),checkitem_dto.getDescription(),user);
        CheckItemTemplate saveditemtemplate=itemTemplateRepository.save(newitemTemplate);
        if(saveditemtemplate==null)
            return ResponseBuilder.buildError(new InternalServerProblem());
        CheckItem newitem=new CheckItem("uncompleted",saveditemtemplate);
        CheckItem savedcheckitem=itemRepository.save(newitem);
        if(savedcheckitem==null) {
            itemTemplateRepository.deleteById(saveditemtemplate.getId());
            return ResponseBuilder.buildError(new InternalServerProblem());
        }
        return ResponseBuilder.build(
                CheckItemSirenBuilder.build(savedcheckitem.getId(),
                        savedcheckitem.getCheckitem_itemtemplate().getName(),
                        savedcheckitem.getCheckitem_itemtemplate().getDescription(),
                        savedcheckitem.getState())
        );

    }

    @Transactional
    public ResponseEntity<?> update(String authorization, CheckItemRequestDto checkitem_dto){
        Users user=userRepository.findByToken(authorization.split(" ")[1]);
        ValidatorResponse valtUser=CheckItemValidator.validateUser(user);
        if(!valtUser.isValid)
            return ResponseBuilder.buildError(valtUser.problem);
        ValidatorResponse valtcheckItem=CheckItemValidator.validateItemUpdateRequest(checkitem_dto);
        if(!valtcheckItem.isValid)
            return ResponseBuilder.buildError(valtcheckItem.problem);
        CheckItem checkitem= itemRepository.findById(checkitem_dto.getId());
        if(checkitem==null)
            return ResponseBuilder.buildError(new InternalServerProblem());
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
                    return ResponseBuilder.buildError(new InternalServerProblem());
                }
            }
            if(checkitem_dto.getName()!=null)
                itemtemplatetosave.setName(checkitem_dto.getName());
            if(checkitem_dto.getDescription()!=null)
                itemtemplatetosave.setDescription(checkitem_dto.getDescription());
            checkitem.setCheckitem_itemtemplate(itemtemplatetosave);
        }
        return ResponseBuilder.build(
                CheckItemSirenBuilder.build(checkitem.getId(),
                        checkitem.getCheckitem_itemtemplate().getName(),
                        checkitem.getCheckitem_itemtemplate().getDescription(),
                        checkitem.getState())
        );
    }


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
            return ResponseBuilder.buildError(new InternalServerProblem());
        long numbTempuses=itemRepository.countByTemplateId(checkItem.getCheckitem_itemtemplate().getId());
        if(numbTempuses==0) {
            long delt_itemtemp_res=itemTemplateRepository.deleteById(checkItem.getCheckitem_itemtemplate().getId());
            if(delt_itemtemp_res==0){
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); //set rollback
                return ResponseBuilder.buildError(new InternalServerProblem());
            }
        }
        //todo:change response
        return ResponseBuilder.build(
                CheckItemSirenBuilder.build(checkItem.getId(),
                        checkItem.getCheckitem_itemtemplate().getName(),
                        checkItem.getCheckitem_itemtemplate().getDescription(),
                        checkItem.getState())
        );
    }

}
