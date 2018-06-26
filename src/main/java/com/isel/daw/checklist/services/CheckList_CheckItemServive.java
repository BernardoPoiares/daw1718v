package com.isel.daw.checklist.services;

import com.isel.daw.checklist.ValidatorResponse;
import com.isel.daw.checklist.model.DataBaseDTOs.CheckItem;
import com.isel.daw.checklist.model.DataBaseDTOs.CheckItemTemplate;
import com.isel.daw.checklist.model.DataBaseDTOs.CheckList;
import com.isel.daw.checklist.model.DataBaseDTOs.Users;
import com.isel.daw.checklist.model.RequestsDTO.CheckItemRequestDto;
import com.isel.daw.checklist.model.RequestsDTO.CheckListRequestDto;
import com.isel.daw.checklist.model.ResponseBuilder;
import com.isel.daw.checklist.model.Validators.CheckItemValidator;
import com.isel.daw.checklist.model.Validators.CheckListValidator;
import com.isel.daw.checklist.problems.InternalServerProblem;
import com.isel.daw.checklist.repositories.CheckItemRepository;
import com.isel.daw.checklist.repositories.CheckListRepository;
import com.isel.daw.checklist.repositories.CheckListTemplateRepository;
import com.isel.daw.checklist.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.transaction.Transactional;

@Component
public class CheckList_CheckItemServive {

    private final CheckListRepository checkListRepository;
    private final UserRepository userRepository;
    private final CheckItemRepository checkItemRepository;


    @Autowired
    public CheckList_CheckItemServive(CheckListRepository listRepository, UserRepository userRepository,CheckItemRepository checkItemRepository){
        this.checkListRepository=listRepository;
        this.userRepository=userRepository;
        this.checkItemRepository=checkItemRepository;
    }


    @Transactional
    public ResponseEntity<?> addCheckItems(String authorization, CheckListRequestDto checklist_dto){
        Users user=userRepository.findByToken(authorization.split(" ")[1]);
        ValidatorResponse valtUser= CheckListValidator.validateUser(user);
        if(!valtUser.isValid)
            return ResponseBuilder.buildError(valtUser.problem);
        ValidatorResponse valtcheckList=CheckListValidator.validateListAddCheckItemsRequest(checklist_dto);
        if(!valtUser.isValid)
            return ResponseBuilder.buildError(valtcheckList.problem);
        CheckList checklist= checkListRepository.findById(checklist_dto.getId());
        if(checklist==null)
            return ResponseBuilder.buildError(new InternalServerProblem());
        for (CheckItemRequestDto checkitem_dto:checklist_dto.getCheckitems()) {
            ValidatorResponse valtcheckitem= CheckItemValidator.validateItemCreateRequest(checkitem_dto);
            if(!valtcheckitem.isValid) {
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); //set rollback
                return ResponseBuilder.buildError(valtcheckList.problem);
            }
            CheckItem checkitem_saved=checkItemRepository.save(new CheckItemTemplate(checkitem_dto.getName(),checkitem_dto.getDescription(),checklisttemplate,user));
            if(checkitem_saved==null){
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); //set rollback
                return ResponseBuilder.buildError(new InternalServerProblem());
            }
        }
        return ResponseBuilder.build(
                CheckListTemplateSirenBuilder.build(checklisttemplate.getId(),
                        checklisttemplate.getName())
        );
    }
}
