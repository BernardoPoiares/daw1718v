package com.isel.daw.checklist.services;

import com.isel.daw.checklist.ServiceResponse;
import com.isel.daw.checklist.model.DataBaseDTOs.CheckItem;
import com.isel.daw.checklist.model.DataBaseDTOs.CheckItemTemplate;
import com.isel.daw.checklist.model.DataBaseDTOs.CheckList;
import com.isel.daw.checklist.model.DataBaseDTOs.CheckListTemplate;
import com.isel.daw.checklist.model.RequestsDTO.CheckItemRequestDto;
import com.isel.daw.checklist.model.RequestsDTO.CheckItem_CheckListRequestDto;
import com.isel.daw.checklist.model.RequestsDTO.CheckListTemplate_CheckListRequestDto;
import com.isel.daw.checklist.repositories.CheckItemRepository;
import com.isel.daw.checklist.repositories.CheckListRepository;
import com.isel.daw.checklist.repositories.CheckListTemplateRepository;
import com.isel.daw.checklist.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.interceptor.TransactionAspectSupport;


import org.springframework.transaction.annotation.Transactional;
import java.util.HashSet;
import java.util.Set;

@Component
public class CheckListTemplate_CheckListService {

    private final CheckListService checkListService;
    private final CheckListTemplateService listTemplateService;
    private final CheckItemService checkItemService;
    private final CheckItemRepository checkItemRepository;


    @Autowired
    public CheckListTemplate_CheckListService(CheckListService checkListService, CheckItemService checkItemService, CheckListTemplateService listTemplateService, CheckItemRepository checkItemRepository) {
        this.checkListService = checkListService;
        this.checkItemService = checkItemService;
        this.listTemplateService = listTemplateService;
        this.checkItemRepository = checkItemRepository;
    }

    @Transactional
    public ServiceResponse<?> createListfromListTemplate(String authorization, CheckListTemplate_CheckListRequestDto checklisttemplate_checklist_dto) {
        ServiceResponse<CheckListTemplate> cklt_resp=listTemplateService.getListById(authorization,checklisttemplate_checklist_dto.getChecklisttemplate_dto().getId());
        if(cklt_resp.getError()!=null)
            return cklt_resp;
        CheckListTemplate checklisttemplate=cklt_resp.getResponse();
        checklisttemplate_checklist_dto.getChecklist_dto().setCheckListTemplate(checklisttemplate); //add this checklisttemplate to the data of the new checklist
        ServiceResponse<CheckList> ckl_resp=checkListService.create(authorization,checklisttemplate_checklist_dto.getChecklist_dto());
        if(ckl_resp.getError()!=null)
            return ckl_resp;
        CheckList checklist=ckl_resp.getResponse();
        Set<CheckItem> checkItems=new HashSet<CheckItem>();
        for(CheckItemTemplate checkitemtemplate:checklisttemplate.getCheckitemtemplate_checklisttemplate()){
            ServiceResponse<CheckItem> cki_resp=checkItemService.createByTemplate(checkitemtemplate);
            if(cki_resp.getError()!=null) {
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); //set rollback
                return cki_resp;
            }
            checkItems.add(cki_resp.getResponse());
        }
        checklist.setCheckItems(checkItems);
        //todo:Change Response
        return new ServiceResponse<CheckList>(checklist,null);
    }

}
