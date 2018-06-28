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


    //TODO:CHANGE TO NEW SERVICE checkitemtemplate_checkitem
    @Transactional
    public ServiceResponse<CheckItem> create(String authorization, CheckItemRequestDto checkitem_dto){
        Users user=userRepository.findByToken(authorization.split(" ")[1]);
        ValidatorResponse valtrequest=CheckItemValidator.validateCkitDto(checkitem_dto);
        if(!valtrequest.isValid)
            return new ServiceResponse<>(null,valtrequest.problem);
        CheckItemTemplate newitemTemplate=new CheckItemTemplate(checkitem_dto.getCheckitemtemplate().getName(),checkitem_dto.getCheckitemtemplate().getDescription(),user);
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


    //TODO:CHANGE TO NEW SERVICE checkitemtemplate_checkitem
    /*
    @Transactional
    public ServiceResponse<?> createByTemplate(String authorization, CheckItemTemplateRequestDto checkitemtemplate_dto){
        Users user=userRepository.findByToken(authorization.split(" ")[1]);
        ValidatorResponse valtrequest=CheckItemValidator.validatCkitTemp(checkitemtemplate_dto);
        if(!valtrequest.isValid)
            return new ServiceResponse<>(null,valtrequest.problem);
        ServiceResponse<CheckItemTemplate> service_res=checkItemTemplateService.getById(authorization,checkitemtemplate_dto.getId());
        if(service_res.getError()!=null)
            return service_res;
        CheckItem newitem=new CheckItem("uncompleted",service_res.getResponse());
        CheckItem savedcheckitem=itemRepository.save(newitem);
        if(savedcheckitem==null)
            return new ServiceResponse<>(null, new InternalServerProblem());
        return new ServiceResponse<>(savedcheckitem,null);
    }*/

    @Transactional
    public ServiceResponse<CheckItem> createByTemplate(CheckItemTemplate checkitemtemplate){
        CheckItem newitem=new CheckItem("uncompleted",checkitemtemplate);
        CheckItem savedcheckitem=itemRepository.save(newitem);
        if(savedcheckitem==null)
            return new ServiceResponse<>(null, new InternalServerProblem());
        return new ServiceResponse<>(savedcheckitem,null);
    }


    //TODO:CHANGE TO NEW SERVICE checkitemtemplate_checkitem
    @Transactional
    public ServiceResponse<?> update(String authorization, CheckItemRequestDto checkitem_dto){
        ServiceResponse<CheckItem> serv_resp=getItemById(authorization,checkitem_dto.getId());
        if(serv_resp.getError()!=null)
            return serv_resp;
        CheckItem checkitem=serv_resp.getResponse();
        if(checkitem_dto.getState()!=null)
            checkitem.setState(checkitem_dto.getState());
        CheckItemTemplateRequestDto ckit_dto=checkitem_dto.getCheckitemtemplate();
        ValidatorResponse valtrequest= CheckItemTemplateValidator.validateUpdateRequest(ckit_dto);
        if(!valtrequest.isValid)
            return new ServiceResponse<>(null,valtrequest.problem);
        CheckItemTemplate itemtemplatetoupdate=checkitem.getCheckitem_itemtemplate();
        long numbTempuses=itemRepository.countByTemplateId(ckit_dto.getId());
        if(numbTempuses>1) {
            ServiceResponse<CheckItemTemplate> ckittemp_res=checkItemTemplateService.create(authorization,checkitem_dto.getCheckitemtemplate());
            if(ckittemp_res.getError()!=null)
                return ckittemp_res;
            itemtemplatetoupdate=ckittemp_res.getResponse();
            checkitem.setCheckitem_itemtemplate(itemtemplatetoupdate);
        }
        if(ckit_dto.getName()!=null)
            itemtemplatetoupdate.setName(ckit_dto.getName());
        if(ckit_dto.getDescription()!=null)
            itemtemplatetoupdate.setDescription(ckit_dto.getDescription());
        return new ServiceResponse<>(checkitem,null);
    }

    //TODO:CHANGE TO NEW SERVICE checkitemtemplate_checkitem
    @Transactional
    public ServiceResponse<CheckItem> delete(String authorization, long id){
        ServiceResponse<CheckItem> serv_resp=getItemById(authorization,id);
        if(serv_resp.getError()!=null)
            return serv_resp;
        CheckItem checkitem=serv_resp.getResponse();
        long delt_item_res= itemRepository.deleteById(checkitem.getId());
        if(delt_item_res==0)
            return new ServiceResponse<>(null,new InternalServerProblem());
        long numbTempuses=itemRepository.countByTemplateId(checkitem.getCheckitem_itemtemplate().getId());
        if(numbTempuses==0) {
            long delt_itemtemp_res=itemTemplateRepository.deleteById(checkitem.getCheckitem_itemtemplate().getId());
            if(delt_itemtemp_res==0){
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); //set rollback
                return new ServiceResponse<>(null,new InternalServerProblem());
            }
        }
        return new ServiceResponse<>(checkitem,null);
    }

}
