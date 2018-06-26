package com.isel.daw.checklist.services;

import com.isel.daw.checklist.ServiceResponse;
import com.isel.daw.checklist.ValidatorResponse;
import com.isel.daw.checklist.model.DataBaseDTOs.*;
import com.isel.daw.checklist.model.RequestsDTO.CheckItemRequestDto;
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
public class CheckList_CheckItemServive {

    private final CheckListService checkListService;
    private final CheckItemService checkItemService;
    private final UserRepository userRepository;
    private final CheckItemRepository checkItemRepository;
    private final CheckItems_In_ChecklistsRepository ckits_in_ckltRepository;


    @Autowired
    public CheckList_CheckItemServive(CheckListService checkListService,CheckItemService  checkItemService,UserRepository userRepository,CheckItemRepository checkItemRepository,CheckItems_In_ChecklistsRepository ckits_in_ckltRepository){
        this.checkListService=checkListService;
        this.checkItemService=checkItemService;
        this.userRepository=userRepository;
        this.checkItemRepository=checkItemRepository;
        this.ckits_in_ckltRepository=ckits_in_ckltRepository;
    }


    @Transactional
    public ServiceResponse<?> addCheckItems(String authorization, CheckListRequestDto checklist_dto){
        ServiceResponse<CheckList> checklist_resp=checkListService.getListById(authorization,checklist_dto.getId());
        if(checklist_resp.getError()!=null)
            return checklist_resp;
        for(CheckItemRequestDto checkitem_dto:checklist_dto.getCheckitems()){
            ServiceResponse<CheckItem> checkitem_resp=checkItemService.getItemById(authorization,checklist_dto.getId());
            if(checklist_resp.getError()!=null)
                return checklist_resp;
            long resp=ckits_in_ckltRepository.save(new CheckItem_CheckList(checkitem_resp.getResponse(),checklist_resp.getResponse());
            if(resp==0){
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); //set rollback
                return new ServiceResponse<>(null,new InternalServerProblem());
            }
        }
        //todo:Change Response
        return new ServiceResponse<>(checklist_resp.getResponse(),null);
    }
}
