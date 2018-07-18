package com.isel.daw.checklist.services;

import com.isel.daw.checklist.ServiceResponse;
import com.isel.daw.checklist.ValidatorResponse;
import com.isel.daw.checklist.model.DataBaseDTOs.CheckItem;
import com.isel.daw.checklist.model.DataBaseDTOs.CheckItemTemplate;
import com.isel.daw.checklist.Service;
import com.isel.daw.checklist.model.DataBaseDTOs.Users;
import com.isel.daw.checklist.model.RequestsDTO.CheckItemTemplateRequestDto;
import com.isel.daw.checklist.model.Validators.CheckItemTemplateValidator;
import com.isel.daw.checklist.model.Validators.CheckItemValidator;
import com.isel.daw.checklist.problems.InternalServerProblem;
import com.isel.daw.checklist.repositories.CheckItemTemplateRepository;
import com.isel.daw.checklist.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CheckItemTemplateService implements Service {

    private final CheckItemTemplateRepository itemTemplateRepository;
    private final UserRepository userRepository;

    @Autowired
    public CheckItemTemplateService(CheckItemTemplateRepository itemTemplateRepository, UserRepository userRepository) {
        this.itemTemplateRepository = itemTemplateRepository;
        this.userRepository=userRepository;
    }

    public ServiceResponse<CheckItemTemplate> create(String authorization, CheckItemTemplateRequestDto ckittemp_dto){
        Users user=userRepository.findByToken(authorization.split(" ")[1]);
        CheckItemTemplate checkItemtemplate= itemTemplateRepository.save(new CheckItemTemplate(ckittemp_dto.getName(),ckittemp_dto.getDescription(),user));
        if(checkItemtemplate==null)
            return new ServiceResponse<>(null, new InternalServerProblem());
        return new ServiceResponse<>(checkItemtemplate,null);
    }

    public ServiceResponse<CheckItemTemplate> clone(String authorization, CheckItemTemplate ckittemp){
        Users user=userRepository.findByToken(authorization.split(" ")[1]);
        CheckItemTemplate checkItemtemplate= itemTemplateRepository.save(new CheckItemTemplate(ckittemp.getName(),ckittemp.getDescription(),user));
        if(checkItemtemplate==null)
            return new ServiceResponse<>(null, new InternalServerProblem());
        return new ServiceResponse<>(checkItemtemplate,null);
    }

    public ServiceResponse<CheckItemTemplate> getById(String authorization,long id){
        Users user=userRepository.findByToken(authorization.split(" ")[1]);
        ValidatorResponse valtid= CheckItemTemplateValidator.validateId(id);
        if(!valtid.isValid)
            return new ServiceResponse<>(null,valtid.problem);
        CheckItemTemplate checkItemtemplate= itemTemplateRepository.findById(id);
        ValidatorResponse valtcheckItemtemplate=CheckItemTemplateValidator.validateItem(checkItemtemplate);
        if(!valtcheckItemtemplate.isValid)
            return new ServiceResponse<>(null,valtcheckItemtemplate.problem);
        return new ServiceResponse<>(checkItemtemplate,null);
    }


}