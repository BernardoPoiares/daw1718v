package com.isel.daw.checklist.services;

import com.isel.daw.checklist.ServiceResponse;
import com.isel.daw.checklist.ValidatorResponse;
import com.isel.daw.checklist.model.*;
import com.isel.daw.checklist.model.DataBaseDTOs.CheckItem;
import com.isel.daw.checklist.model.DataBaseDTOs.CheckItemTemplate;
import com.isel.daw.checklist.model.DataBaseDTOs.CheckList;
import com.isel.daw.checklist.model.DataBaseDTOs.Users;
import com.isel.daw.checklist.model.RequestsDTO.CheckItemRequestDto;
import com.isel.daw.checklist.model.RequestsDTO.CheckListRequestDto;
import com.isel.daw.checklist.model.SirenBuilders.CheckListSirenBuilder;
import com.isel.daw.checklist.model.Validators.CheckItemValidator;
import com.isel.daw.checklist.model.Validators.CheckListValidator;
import com.isel.daw.checklist.problems.InternalServerProblem;
import com.isel.daw.checklist.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.transaction.Transactional;

@Component
public class CheckListService {

    private final CheckListRepository checkListRepository;
    private final CheckListTemplateRepository listTemplateRepository;
    private final UserRepository userRepository;
    private final CheckItemRepository checkItemRepository;


    @Autowired
    public CheckListService(CheckListRepository listRepository, UserRepository userRepository, CheckListTemplateRepository listTemplateService, CheckItemRepository checkItemRepository) {
        this.checkListRepository = listRepository;
        this.userRepository = userRepository;
        this.listTemplateRepository = listTemplateService;
        this.checkItemRepository = checkItemRepository;
    }

    public ServiceResponse<CheckList> getListById(String authorization, long id) {
        Users user = userRepository.findByToken(authorization.split(" ")[1]);
        ValidatorResponse valtUser = CheckListValidator.validateUser(user);
        if (!valtUser.isValid)
            return new ServiceResponse<>(null, valtUser.problem);
        CheckList checklist = checkListRepository.findById(id);
        if (checklist == null)
            return new ServiceResponse<>(null, new InternalServerProblem());
        ValidatorResponse valtcheckList = CheckListValidator.validateListById(checklist, id, user);
        if (!valtcheckList.isValid)
            return new ServiceResponse<>(null, valtcheckList.problem);
        return new ServiceResponse<>(checklist, null);

    }


    public ServiceResponse<CheckList> create(String authorization, CheckListRequestDto checklist_dto) {
        Users user = userRepository.findByToken(authorization.split(" ")[1]);
        ValidatorResponse valtUser = CheckListValidator.validateUser(user);
        if (!valtUser.isValid)
            return new ServiceResponse<>(null, valtUser.problem);
        CheckList newlist = new CheckList(checklist_dto.getName(), user, checklist_dto.getCompletionDate(),checklist_dto.getCheckListTemplate());
        CheckList savedchecklist = checkListRepository.save(newlist);
        if (savedchecklist == null)
            return new ServiceResponse<>(null, new InternalServerProblem());
        return new ServiceResponse<>(savedchecklist, null);
    }

    @Transactional
    public ServiceResponse<CheckList> update(String authorization, CheckListRequestDto checklist_dto) {
        Users user = userRepository.findByToken(authorization.split(" ")[1]);
        ValidatorResponse valtcheckList = CheckListValidator.validateListRequest(checklist_dto);
        if (!valtcheckList.isValid)
            return new ServiceResponse<>(null, valtcheckList.problem);
        CheckList checklist = checkListRepository.findById(checklist_dto.getId());
        if (checklist == null)
            return new ServiceResponse<>(null, new InternalServerProblem());
        if (checklist_dto.getName() != null)
            checklist.setName(checklist_dto.getName());
        if (checklist_dto.getCompletionDate() != null)
            checklist.setCompletionDate(checklist_dto.getCompletionDate());
        CheckList updatedchecklist = checkListRepository.save(checklist);
        if (updatedchecklist == null)
            return new ServiceResponse<>(null, new InternalServerProblem());
        return new ServiceResponse<>(updatedchecklist, null);
    }


    @Transactional
    public ServiceResponse<CheckList> delete(String authorization, long id) {
        Users user = userRepository.findByToken(authorization.split(" ")[1]);
        ValidatorResponse valtUser = CheckListValidator.validateUser(user);
        if (!valtUser.isValid)
            return new ServiceResponse<>(null, valtUser.problem);
        CheckList checklist = checkListRepository.findById(id);
        if (checklist == null)
            return new ServiceResponse<>(null, new InternalServerProblem());
        ValidatorResponse valtcheckList = CheckListValidator.validateListById(checklist, id, user);
        if (!valtcheckList.isValid)
            return new ServiceResponse<>(null, valtcheckList.problem);
        long delt_list_res = checkListRepository.deleteById(id);
        if (delt_list_res == 0)
            return new ServiceResponse<>(null, new InternalServerProblem());         //todo:check transactional better
        //todo:change response
        return new ServiceResponse<>(checklist, null);
    }
}


/*

    @Transactional
    public ServiceResponse<CheckList> deleteCheckItemsTemplate(String authorization, CheckListTemplateRequestDto checklisttemplate_dto){
        Users user=userRepository.findByToken(authorization.split(" ")[1]);
        ValidatorResponse valtUser=CheckListTemplateValidator.validateUser(user);
        if(!valtUser.isValid)
            return ResponseBuilder.buildError(valtUser.problem);
        ValidatorResponse valtcheckList=CheckListTemplateValidator.validateListTemplatetoDeleteItemsRequest(checklisttemplate_dto);
        if(!valtUser.isValid)
            return ResponseBuilder.buildError(valtcheckList.problem);
        CheckListTemplate checklisttemplate= listTemplateRepository.findById(checklisttemplate_dto.getId());
        if(checklisttemplate==null)
            return ResponseBuilder.buildError(new InternalServerProblem());
        for (CheckItemRequestDto checkitem_dto:checklisttemplate_dto.getCheckitems()) { //todo:create itemtemplaterequestdto
            CheckItemTemplate checkItemTemplate= itemTemplateRepository.getById(checkitem_dto.getId());
            ValidatorResponse valtcheckitem= CheckItemTemplateValidator.validateDeltReqTempList(checkItemTemplate,user,checklisttemplate);
            if(!valtcheckitem.isValid) {
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); //set rollback
                return ResponseBuilder.buildError(valtcheckList.problem);
            }
            long del_res=itemTemplateRepository.deleteById(checkItemTemplate.getId());
            if(del_res==0){
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); //set rollback
                return ResponseBuilder.buildError(new InternalServerProblem());
            }
        }
        return ResponseBuilder.build(
                CheckListTemplateSirenBuilder.build(checklisttemplate.getId(),
                        checklisttemplate.getName())
        );
    }


}*/
