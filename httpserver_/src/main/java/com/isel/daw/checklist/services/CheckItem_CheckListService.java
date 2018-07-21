package com.isel.daw.checklist.services;

import com.isel.daw.checklist.Converter;
import com.isel.daw.checklist.ServiceResponse;
import com.isel.daw.checklist.ValidatorResponse;
import com.isel.daw.checklist.model.DataBaseDTOs.*;
import com.isel.daw.checklist.model.RequestsDTO.CheckItemRequestDto;
import com.isel.daw.checklist.model.RequestsDTO.CheckItem_CheckListRequestDto;
import com.isel.daw.checklist.model.RequestsDTO.CheckListRequestDto;
import com.isel.daw.checklist.model.ResponseBuilder;
import com.isel.daw.checklist.model.Validators.CheckItemValidator;
import com.isel.daw.checklist.model.Validators.CheckListValidator;
import com.isel.daw.checklist.problems.InternalServerProblem;
import com.isel.daw.checklist.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.List;

@Component
public class CheckItem_CheckListService {

    private final CheckListService checkListService;
    private final CheckItemService checkItemService;
    private final CheckItemTemplate_CheckItemService checkItem_Template_Service;
    private final CheckListRepository checkListRepository;


    @Autowired
    public CheckItem_CheckListService(CheckListService checkListService,CheckItemService  checkItemService,CheckItemTemplate_CheckItemService checkItem_Template_Service,CheckListRepository checkListRepository){
        this.checkListService=checkListService;
        this.checkItemService=checkItemService;
        this.checkItem_Template_Service=checkItem_Template_Service;
        this.checkListRepository=checkListRepository;
    }


    @Transactional
    public ServiceResponse<?> addCheckItems(String authorization, CheckItem_CheckListRequestDto checklist_dto){
        ServiceResponse<CheckList> checklist_resp=checkListService.getListById(authorization,checklist_dto.getId());
        if(checklist_resp.getError()!=null)
            return checklist_resp;
        CheckList checklist=checklist_resp.getResponse();
        for(CheckItemRequestDto checkitem_dto:checklist_dto.getCheckitems()){
            ServiceResponse<CheckItem> checkitem_resp=checkItemService.getCheckItem(authorization,checkitem_dto.getId());
            if(checkitem_resp.getError()!=null) {
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); //set rollback
                return checkitem_resp;
            }
            checklist.addCheckItems(checkitem_resp.getResponse());
           /* if(resp!=null){
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); //set rollback
                return new ServiceResponse<>(null,new InternalServerProblem());
            }*/
        }
        //todo:Change Response
        return new ServiceResponse<>(checklist_resp.getResponse(),null);
    }


    @Transactional
    public ServiceResponse<?> deleteCheckItems(String authorization, CheckItem_CheckListRequestDto checklist_dto){
        ServiceResponse<CheckList> checklist_resp=checkListService.getListById(authorization,checklist_dto.getId());
        if(checklist_resp.getError()!=null)
            return checklist_resp;
        CheckList checklist=checklist_resp.getResponse();
        for(CheckItemRequestDto checkitem_dto:checklist_dto.getCheckitems()){
            ServiceResponse<CheckItem> checkitem_resp=checkItemService.getCheckItem(authorization,checkitem_dto.getId());
            if(checkitem_resp.getError()!=null) {
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); //set rollback
                return checklist_resp;
            }checklist.removeCheckItems(checkitem_resp.getResponse());
           /* if(resp!=null){
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); //set rollback
                return new ServiceResponse<>(null,new InternalServerProblem());
            }*/
        }
        //todo:Change Response
        return new ServiceResponse<>(checklist_resp.getResponse(),null);
    }

    @Transactional
    public ServiceResponse<?> createAndAdd(String authorization, CheckItem_CheckListRequestDto checklist_dto) {
        ServiceResponse<CheckItem> checkitem_resp=checkItem_Template_Service.create(authorization,checklist_dto.getCheckitems()[0]);
        if(checkitem_resp.getError()!=null) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); //set rollback
            return checkitem_resp;
        }
        ServiceResponse<CheckList> checklist_resp=checkListService.getListById(authorization,checklist_dto.getId());
        if(checklist_resp.getError()!=null)
            return checklist_resp;
        CheckList checkList=checklist_resp.getResponse();
        checkList.addCheckItems(checkitem_resp.getResponse());
        return new ServiceResponse<>(checkList,null);
    }

    @Transactional(isolation = Isolation.SERIALIZABLE,propagation= Propagation.REQUIRES_NEW)
    public ServiceResponse<?> searchByItem(String authorization,Long item_id){
        ServiceResponse<CheckItem> checkitem_resp=checkItemService.getCheckItem(authorization,item_id);
        if(checkitem_resp.getError()!=null) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); //set rollback
            return checkitem_resp;
        }
        CheckItem checkitem=checkitem_resp.getResponse();
        List<CheckList> checklists= checkListRepository.findAllByCheckItems(checkitem);
        return new ServiceResponse<>(Converter.CheckListsDTO_CheckLists(checklists.toArray(new CheckList[checklists.size()])),null);
    }

}
