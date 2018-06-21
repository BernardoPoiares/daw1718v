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
        ValidatorResponse valtcheckItem=CheckItemValidator.validateItemRequest(checkitem_dto);
        if(!valtcheckItem.isValid)
            return ResponseBuilder.buildError(valtcheckItem.problem);
        CheckItem checkItem= itemRepository.findById(checkitem_dto.getId());
        if(checkitem_dto.getState()!=null) {
            checkItem.setState(checkitem_dto.getState());
            itemRepository.save(checkItem);
        }
        if(checkitem_dto.getName()!=null || checkitem_dto.getDescription()!=null){
            String newtemplate_name=checkitem_dto.getName()!=null?checkitem_dto.getName():checkItem.getCheckitem_itemtemplate().getName();
            String newtemplate_description=checkitem_dto.getDescription()!=null?checkitem_dto.getDescription():checkItem.getCheckitem_itemtemplate().getDescription();
            CheckItemTemplate newitemTemplate=new CheckItemTemplate(newtemplate_name,newtemplate_description,user);
            CheckItemTemplate saveditemtemplate=itemTemplateRepository.save(newitemTemplate);
            if(saveditemtemplate==null)
                return ResponseBuilder.buildError(new InternalServerProblem());
            checkItem.setCheckitem_itemtemplate(saveditemtemplate);
            CheckItem savedcheckitem=itemRepository.save(checkItem);
            if(savedcheckitem==null)
                return ResponseBuilder.buildError(new InternalServerProblem());
        }
        return ResponseBuilder.build(
                CheckItemSirenBuilder.build(checkItem.getId(),
                        checkItem.getCheckitem_itemtemplate().getName(),
                        checkItem.getCheckitem_itemtemplate().getDescription(),
                        checkItem.getState())
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
        long delt_item_res= itemRepository.deleteById(5);
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
    }

}
