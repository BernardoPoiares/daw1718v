package com.isel.daw.checklist.services;

import com.isel.daw.checklist.Converter;
import com.isel.daw.checklist.ServiceResponse;
import com.isel.daw.checklist.ValidatorResponse;
import com.isel.daw.checklist.model.DataBaseDTOs.CheckItem;
import com.isel.daw.checklist.model.DataBaseDTOs.CheckItemTemplate;
import com.isel.daw.checklist.Service;
import com.isel.daw.checklist.model.DataBaseDTOs.CheckListTemplate;
import com.isel.daw.checklist.model.DataBaseDTOs.Users;
import com.isel.daw.checklist.model.RequestsDTO.CheckItemTemplateRequestDto;
import com.isel.daw.checklist.model.ResponseBuilder;
import com.isel.daw.checklist.model.Validators.CheckItemTemplateValidator;
import com.isel.daw.checklist.model.Validators.CheckItemValidator;
import com.isel.daw.checklist.model.Validators.CheckListTemplateValidator;
import com.isel.daw.checklist.problems.InternalServerProblem;
import com.isel.daw.checklist.repositories.CheckItemTemplateRepository;
import com.isel.daw.checklist.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.List;

@Component
public class CheckItemTemplateService implements Service {

    private final CheckItemTemplateRepository itemTemplateRepository;
    private final UserRepository userRepository;

    @Autowired
    public CheckItemTemplateService(CheckItemTemplateRepository itemTemplateRepository, UserRepository userRepository) {
        this.itemTemplateRepository = itemTemplateRepository;
        this.userRepository = userRepository;
    }


    @Transactional
    public ServiceResponse<CheckItemTemplate> create(String authorization, CheckItemTemplateRequestDto ckittemp_dto, CheckListTemplate checkListTemplate) {
        Users user = userRepository.findByToken(authorization.split(" ")[1]);
        ValidatorResponse valtcheckitem = CheckItemTemplateValidator.validateCreateRequest(ckittemp_dto, user);
        if (!valtcheckitem.isValid)
            return new ServiceResponse<>(null, valtcheckitem.problem);
        CheckItemTemplate checkitem_saved = itemTemplateRepository.save(new CheckItemTemplate(ckittemp_dto.getName(), ckittemp_dto.getDescription(), checkListTemplate, user));
        if (checkitem_saved == null)
            return new ServiceResponse<>(null, new InternalServerProblem());
        return new ServiceResponse<>(checkitem_saved, null);
    }

    @Transactional
    public ServiceResponse<CheckItemTemplate> delete(String authorization, long id) {
        ServiceResponse<CheckItemTemplate> serv_resp = getById(authorization, id);
        if (serv_resp.getError() != null)
            return serv_resp;
        long res_delete = itemTemplateRepository.deleteById(id);
        if (res_delete == 0)
            return new ServiceResponse<>(null, new InternalServerProblem());
        return new ServiceResponse<>(serv_resp.getResponse(), null);
    }


    @Transactional
    public ServiceResponse<CheckItemTemplate> clone(String authorization, CheckItemTemplate ckittemp) {
        Users user = userRepository.findByToken(authorization.split(" ")[1]);
        CheckItemTemplate checkItemtemplate = itemTemplateRepository.save(new CheckItemTemplate(ckittemp.getName(), ckittemp.getDescription(), user));
        if (checkItemtemplate == null)
            return new ServiceResponse<>(null, new InternalServerProblem());
        return new ServiceResponse<>(checkItemtemplate, null);
    }


    @Transactional
    public ServiceResponse<CheckItemTemplate> getById(String authorization, long id) {
        Users user = userRepository.findByToken(authorization.split(" ")[1]);
        ValidatorResponse valtid = CheckItemTemplateValidator.validateId(id);
        if (!valtid.isValid)
            return new ServiceResponse<>(null, valtid.problem);
        CheckItemTemplate checkItemtemplate = itemTemplateRepository.findById(id);
        ValidatorResponse valtcheckItemtemplate = CheckItemTemplateValidator.validateItem(checkItemtemplate, user);
        if (!valtcheckItemtemplate.isValid)
            return new ServiceResponse<>(null, valtcheckItemtemplate.problem);
        return new ServiceResponse<>(checkItemtemplate, null);
    }


    @Transactional
    public ServiceResponse<CheckItemTemplateRequestDto[]> getByList(String authorization, CheckListTemplate checkListTemplate) {
        Users user = userRepository.findByToken(authorization.split(" ")[1]);
        ValidatorResponse valtid = CheckListTemplateValidator.validateListTemplate(checkListTemplate, user);
        if (!valtid.isValid)
            return new ServiceResponse<>(null, valtid.problem);
        List<CheckItemTemplate> checkItemtemplates = itemTemplateRepository.findAllByCheckitemtemplateChecklisttemplate(checkListTemplate);
        ValidatorResponse valtcheckItemtemplate = CheckItemTemplateValidator.validateItems(checkItemtemplates, user);
        if (!valtcheckItemtemplate.isValid)
            return new ServiceResponse<>(null, valtcheckItemtemplate.problem);
        return new ServiceResponse<>(Converter.CheckItemsTemplatesDTO_CheckItemsTemplates(checkItemtemplates), null);
    }


    @Transactional
    public ServiceResponse<?> deleteVarious(String authorization, CheckItemTemplateRequestDto[] checkitemstemps_dto) {
        for (CheckItemTemplateRequestDto checkitemtemp : checkitemstemps_dto) {
            ServiceResponse<CheckItemTemplate> serv_resp = delete(authorization, checkitemtemp.getId());
            if (serv_resp.getError() != null) {
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); //set rollback
                return serv_resp;
            }
        }
        return new ServiceResponse<>(checkitemstemps_dto, null);
    }

}