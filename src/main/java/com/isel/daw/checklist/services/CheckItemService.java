package com.isel.daw.checklist.services;

import com.isel.daw.checklist.ValidatorResponse;
import com.isel.daw.checklist.model.*;
import com.isel.daw.checklist.Service;
import com.isel.daw.checklist.model.RequestsDTO.CheckItemRequestDto;
import com.isel.daw.checklist.model.SirenBuilders.CheckItemSirenBuilder;
import com.isel.daw.checklist.repositories.CheckItemRepository;
import com.isel.daw.checklist.repositories.CheckItemTemplateRepository;
import com.isel.daw.checklist.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

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
        //if(checkItem==null)
           // return new ResponseEntity<>("Item does not exist",HttpStatus.BAD_REQUEST);
        //ValidatorResponse valt=CheckItemValidator.validateItemWithUser(checkItem.getCheckitem_itemtemplate().getItemTemplate_user().getUserName(),user.getUserName());
       // if(!valt.isValid)
         //   return new ResponseEntity<>(valt.error.message,valt.error.httpStatus);
      return ResponseBuilder.build(
              CheckItemSirenBuilder.build(checkItem.getId(),
                checkItem.getCheckitem_itemtemplate().getName(),
                checkItem.getCheckitem_itemtemplate().getDescription(),
                checkItem.getState())
            );
    }

    public ResponseEntity<?> create(String authorization, CheckItemRequestDto checkitem_dto){
        Users user=userRepository.findByToken(authorization.split(" ")[1]);
        CheckItemTemplate newitemTemplate=new CheckItemTemplate(checkitem_dto.getName(),checkitem_dto.getDescription(),user);
        CheckItemTemplate saveditemtemplate=itemTemplateRepository.save(newitemTemplate);
        CheckItem newitem=new CheckItem("uncompleted",saveditemtemplate);
        CheckItem savedcheckitem=itemRepository.save(newitem);
        return new ResponseEntity<>(
                savedcheckitem,
                HttpStatus.ACCEPTED
        );

    }


}
