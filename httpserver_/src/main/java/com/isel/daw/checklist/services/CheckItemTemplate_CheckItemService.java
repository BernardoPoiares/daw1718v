package com.isel.daw.checklist.services;

import com.isel.daw.checklist.Converter;
import com.isel.daw.checklist.Service;
import com.isel.daw.checklist.ServiceResponse;
import com.isel.daw.checklist.ValidatorResponse;
import com.isel.daw.checklist.model.DataBaseDTOs.CheckItem;
import com.isel.daw.checklist.model.DataBaseDTOs.CheckItemTemplate;
import com.isel.daw.checklist.model.DataBaseDTOs.CheckList;
import com.isel.daw.checklist.model.DataBaseDTOs.Users;
import com.isel.daw.checklist.model.RequestsDTO.CheckItemRequestDto;
import com.isel.daw.checklist.model.RequestsDTO.CheckItemTemplateRequestDto;
import com.isel.daw.checklist.model.Validators.CheckItemTemplateValidator;
import com.isel.daw.checklist.model.Validators.CheckItemValidator;
import com.isel.daw.checklist.problems.InternalServerProblem;
import com.isel.daw.checklist.repositories.CheckItemRepository;
import com.isel.daw.checklist.repositories.CheckItemTemplateRepository;
import com.isel.daw.checklist.repositories.UserRepository;
import org.hibernate.annotations.Check;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.interceptor.TransactionAspectSupport;


import org.springframework.transaction.annotation.Transactional;

@Component
public class CheckItemTemplate_CheckItemService implements Service {

    private final CheckItemRepository itemRepository;
    private final CheckItemTemplateRepository itemTemplateRepository;
    private final UserRepository userRepository;
    private final CheckItemTemplateService checkItemTemplateService;
    private final CheckItemService checkItemService;

    @Autowired
    public CheckItemTemplate_CheckItemService(CheckItemRepository itemRepository, UserRepository userRepository, CheckItemTemplateRepository itemTemplateRepository,CheckItemTemplateService checkItemTemplateService,CheckItemService checkItemService) {
        this.itemRepository = itemRepository;
        this.userRepository = userRepository;
        this.itemTemplateRepository = itemTemplateRepository;
        this.checkItemTemplateService = checkItemTemplateService;
        this.checkItemService=checkItemService;
    }



    @Transactional
    public ServiceResponse<CheckItem> create(String authorization, CheckItemRequestDto checkitem_dto){
        Users user=userRepository.findByToken(authorization.split(" ")[1]);
        ValidatorResponse valtrequest= CheckItemValidator.validateCkitDto(checkitem_dto);
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


    @Transactional
    public ServiceResponse<?> update(String authorization, CheckItemRequestDto checkitem_dto){
        ValidatorResponse valtrequest= CheckItemValidator.validateItemUpdateRequest(checkitem_dto);
        if(!valtrequest.isValid)
            return new ServiceResponse<>(null,valtrequest.problem);
        ServiceResponse<CheckItem> serv_resp=checkItemService.getCheckItem(authorization,checkitem_dto.getId());
        if(serv_resp.getError()!=null)
            return serv_resp;
        CheckItem checkitem=serv_resp.getResponse();
        if(checkitem_dto.getState()!=null)
            checkitem.setState(checkitem_dto.getState());
        CheckItemTemplateRequestDto ckit_dto=checkitem_dto.getCheckitemtemplate();
        if(ckit_dto.getDescription()!=null ||ckit_dto.getName()!=null) {
            CheckItemTemplate itemtemplatetoupdate = checkitem.getCheckitem_itemtemplate();
            long numbTempuses = itemRepository.countByTemplateId(itemtemplatetoupdate.getId());
            if (numbTempuses > 1) {
                ServiceResponse<CheckItemTemplate> ckittemp_res = checkItemTemplateService.clone(authorization, itemtemplatetoupdate);
                if (ckittemp_res.getError() != null)
                    return ckittemp_res;

                itemtemplatetoupdate = ckittemp_res.getResponse();
                checkitem.setCheckitem_itemtemplate(itemtemplatetoupdate);
            }
            if (ckit_dto.getName() != null)
                itemtemplatetoupdate.setName(ckit_dto.getName());
            if (ckit_dto.getDescription() != null)
                itemtemplatetoupdate.setDescription(ckit_dto.getDescription());
        }
        return new ServiceResponse<>(checkitem,null);
    }

    @Transactional(isolation = Isolation.SERIALIZABLE ,propagation= Propagation.REQUIRES_NEW)
    public ServiceResponse<CheckItem> delete(String authorization, long id){
        ServiceResponse<CheckItem> serv_resp=checkItemService.getCheckItem(authorization,id);
        if(serv_resp.getError()!=null)
            return serv_resp;
        CheckItem checkitem=serv_resp.getResponse();
        removeCheckItemFromCheckLists(checkitem);
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


    @Transactional(isolation = Isolation.SERIALIZABLE ,propagation= Propagation.REQUIRES_NEW)
    public ServiceResponse<CheckItemRequestDto[]> deleteVarious(String authorization, CheckItemRequestDto[] checkItemsRequestDto){
        for(CheckItemRequestDto ci:checkItemsRequestDto){
            ServiceResponse<CheckItem> servresp=delete(authorization,ci.getId());
            if(servresp.getError()!=null){
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); //set rollback
                return new ServiceResponse<>(null,servresp.getError());
            }
        }
        return new ServiceResponse<>(checkItemsRequestDto,null);
    }


    void removeCheckItemFromCheckLists(CheckItem checkItem){
        for(CheckList checkList:checkItem.getCheckLists())
            checkList.removeCheckItems(checkItem);
        checkItem.removeCheckLists();
    }


}
