package com.isel.daw.checklist.services;

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
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.transaction.Transactional;
import java.util.List;

@Component
public class CheckItem_CheckListService {

    private final CheckListService checkListService;
    private final CheckItemService checkItemService;
    private final UserRepository userRepository;
    private final CheckItemRepository checkItemRepository;


    @Autowired
    public CheckItem_CheckListService(CheckListService checkListService,CheckItemService  checkItemService,UserRepository userRepository,CheckItemRepository checkItemRepository){
        this.checkListService=checkListService;
        this.checkItemService=checkItemService;
        this.userRepository=userRepository;
        this.checkItemRepository=checkItemRepository;
    }


    @Transactional
    public ServiceResponse<?> addCheckItems(String authorization, CheckItem_CheckListRequestDto checklist_dto){
        ServiceResponse<CheckList> checklist_resp=checkListService.getListById(authorization,checklist_dto.getId());
        if(checklist_resp.getError()!=null)
            return checklist_resp;
        CheckList checklist=checklist_resp.getResponse();
        for(CheckItemRequestDto checkitem_dto:checklist_dto.getCheckitems()){
            ServiceResponse<CheckItem> checkitem_resp=checkItemService.getItemById(authorization,checkitem_dto.getId());
            if(checkitem_resp.getError()!=null) {
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); //set rollback
                return checklist_resp;
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
            ServiceResponse<CheckItem> checkitem_resp=checkItemService.getItemById(authorization,checkitem_dto.getId());
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
}
