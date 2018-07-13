package com.isel.daw.checklist.services;

import com.isel.daw.checklist.Converter;
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
import com.isel.daw.checklist.problems.NotFoundProblem;
import com.isel.daw.checklist.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.transaction.Transactional;

@Component
public class CheckListService {

    private final CheckListRepository checkListRepository;
    private final UserRepository userRepository;


    @Autowired
    public CheckListService(CheckListRepository listRepository, UserRepository userRepository) {
        this.checkListRepository = listRepository;
        this.userRepository = userRepository;
    }

    public ServiceResponse<CheckList> getListById(String authorization, long id) {
        Users user = userRepository.findByToken(authorization.split(" ")[1]);
        ValidatorResponse valrequest=CheckListValidator.valrequestCklById(user,id);
        if (!valrequest.isValid )
            return new ServiceResponse<>(null,valrequest.problem);
        CheckList checklist = checkListRepository.findById(id);
        ValidatorResponse valchecklist=CheckListValidator.valCkl_User(checklist,user,id);
        if (!valchecklist.isValid )
            return new ServiceResponse<>(null, valchecklist.problem);
        return new ServiceResponse<>(checklist, null);

    }

    public ServiceResponse<CheckListRequestDto[]> getAll(String authorization){
        Users user = userRepository.findByToken(authorization.split(" ")[1]);
        CheckList[] checklist = checkListRepository.findAllbyUser(user.getId());
        ValidatorResponse valchecklist=CheckListValidator.valLists(checklist,user);
        if (!valchecklist.isValid )
            return new ServiceResponse<>(null, valchecklist.problem);
        return new ServiceResponse<>(Converter.CheckListsDTO_CheckLists(checklist), null);
    }


    public ServiceResponse<CheckList> create(String authorization, CheckListRequestDto checklist_dto) {
        Users user = userRepository.findByToken(authorization.split(" ")[1]);
        ValidatorResponse valtUser = CheckListValidator.valUser_Ckldto(user,checklist_dto);
        if (!valtUser.isValid)
            return new ServiceResponse<>(null, valtUser.problem);
        CheckList newlist = new CheckList(checklist_dto.getName(), user, checklist_dto.getCompletionDate(),checklist_dto.getCheckListTemplate()); //maybe check listtemplate
        CheckList savedchecklist = checkListRepository.save(newlist);
        if (savedchecklist == null)
            return new ServiceResponse<>(null, new InternalServerProblem());
        return new ServiceResponse<>(savedchecklist, null);
    }

    @Transactional
    public ServiceResponse<CheckList> update(String authorization, CheckListRequestDto checklist_dto) {
        Users user = userRepository.findByToken(authorization.split(" ")[1]);
        ValidatorResponse valtrequest = CheckListValidator.valUpdateRequest(user,checklist_dto);
        if (!valtrequest.isValid)
            return new ServiceResponse<>(null, valtrequest.problem);
        CheckList checklist = checkListRepository.findById(checklist_dto.getId());
        ValidatorResponse valtcheckList = CheckListValidator.valCkl_User(checklist,user,checklist_dto.getId());
        if (!valtcheckList.isValid)
            return new ServiceResponse<>(null, valtcheckList.problem);
        if (checklist_dto.getName() != null)
            checklist.setName(checklist_dto.getName());
        if (checklist_dto.getCompletionDate() != null)
            checklist.setCompletionDate(checklist_dto.getCompletionDate());
        //TODO: LEAVE IT LIKE THIS OR NOT?
        CheckList updatedchecklist = checkListRepository.save(checklist);
        if (updatedchecklist == null)
            return new ServiceResponse<>(null, new InternalServerProblem());
        return new ServiceResponse<>(updatedchecklist, null);
    }


    @Transactional
    public ServiceResponse<CheckList> delete(String authorization, long id) {
        Users user = userRepository.findByToken(authorization.split(" ")[1]);
        ValidatorResponse valtrequest = CheckListValidator.valrequestCklById(user,id);
        if (!valtrequest.isValid)
            return new ServiceResponse<>(null, valtrequest.problem);
        CheckList checklist = checkListRepository.findById(id);
        ValidatorResponse valtcheckList = CheckListValidator.valCkl_User(checklist,user,id);
        if (!valtcheckList.isValid)
            return new ServiceResponse<>(null, valtcheckList.problem);
        long delt_list_res = checkListRepository.deleteById(id);
        if (delt_list_res == 0)
            return new ServiceResponse<>(null, new InternalServerProblem());
        return new ServiceResponse<>(checklist, null);
    }

}