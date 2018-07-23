package com.isel.daw.checklist.services;

import com.isel.daw.checklist.Service;
import com.isel.daw.checklist.ServiceResponse;
import com.isel.daw.checklist.ValidatorResponse;
import com.isel.daw.checklist.model.DataBaseDTOs.CheckItemTemplate;
import com.isel.daw.checklist.model.DataBaseDTOs.CheckList;
import com.isel.daw.checklist.model.DataBaseDTOs.CheckListTemplate;
import com.isel.daw.checklist.model.DataBaseDTOs.Users;
import com.isel.daw.checklist.model.RequestsDTO.CheckItemTemplateRequestDto;
import com.isel.daw.checklist.model.RequestsDTO.CheckListTemplateRequestDto;
import com.isel.daw.checklist.model.Validators.CheckItemTemplateValidator;
import com.isel.daw.checklist.model.Validators.CheckListTemplateValidator;
import com.isel.daw.checklist.problems.InternalServerProblem;
import com.isel.daw.checklist.repositories.CheckItemRepository;
import com.isel.daw.checklist.repositories.CheckItemTemplateRepository;
import com.isel.daw.checklist.repositories.CheckListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.List;


@Component
public class CheckItemTemplate_CheckListTemplateService {

    private final CheckListTemplateService checkListTemplateService;

    private final CheckItemTemplateService checkItemTemplateService;

    private final CheckItemTemplateRepository checkItemTemplateRepository;

    private final CheckListRepository   checkListRepository;

    @Autowired
    public CheckItemTemplate_CheckListTemplateService(CheckListRepository checkListRepository,CheckItemTemplateRepository checkItemTemplateRepository, CheckListTemplateService checkListTemplateService, CheckItemTemplateService checkItemTemplateService){
        this.checkItemTemplateRepository=checkItemTemplateRepository;
        this.checkItemTemplateService=checkItemTemplateService;
        this.checkListTemplateService=checkListTemplateService;
        this.checkListRepository= checkListRepository;

    }

    public ServiceResponse<?> createAndAdd(String authorization,CheckListTemplateRequestDto checklisttemp_dto){
        ServiceResponse<CheckListTemplate> serv_resp_cltp=checkListTemplateService.getListById(authorization,checklisttemp_dto.getId());
        if(serv_resp_cltp.getError()!=null)
            return serv_resp_cltp;
        ServiceResponse<CheckItemTemplate> serv_resp_citp=checkItemTemplateService.create(authorization,checklisttemp_dto.getCheckitemstemplates()[0],serv_resp_cltp.getResponse());
        if(serv_resp_citp.getError()!=null)
            return serv_resp_citp;
        return  new ServiceResponse<>(serv_resp_citp.getResponse(),null);

    }

    public ServiceResponse<?>getByList(String authorization,long checklistemp_id){
        ServiceResponse<CheckListTemplate> serv_resp_cltp=checkListTemplateService.getListById(authorization,checklistemp_id);
        if(serv_resp_cltp.getError()!=null)
            return serv_resp_cltp;
       ServiceResponse<CheckItemTemplateRequestDto[]> ser_resp_citp=checkItemTemplateService.getByList(authorization,serv_resp_cltp.getResponse());
        if(ser_resp_citp.getError()!=null)
            return ser_resp_citp;
        return new ServiceResponse<>(ser_resp_citp.getResponse(),null);
    }

    @Transactional
    public ServiceResponse<CheckListTemplate> delete(String authorization, long id){
        ServiceResponse<CheckListTemplate> serv_resp_cltp=checkListTemplateService.getListById(authorization,id);
        if(serv_resp_cltp.getError()!=null)
            return serv_resp_cltp;
        List<CheckItemTemplate> checkItemTemplates=checkItemTemplateRepository.findAllByCheckitemtemplateChecklisttemplate(serv_resp_cltp.getResponse());
        for(CheckItemTemplate checkItemTemplate:checkItemTemplates){
            checkItemTemplate.setCheckitemtemplate_checklisttemplate(null);
        }
        List<CheckList> checkLists=checkListRepository.findAllByChecklistChecklisttemplate(serv_resp_cltp.getResponse());
        for(CheckList checkList:checkLists){
            checkList.setChecklistChecklisttemplate(null);
        }
        ServiceResponse<CheckListTemplate> serv_resp=checkListTemplateService.delete(authorization,id);
        if(serv_resp.getError()!=null)
            return serv_resp;
        return new ServiceResponse<>(serv_resp.getResponse(),null);
    }
    @Transactional
    public ServiceResponse<CheckListTemplateRequestDto[]> deleteVarious(String authorization, CheckListTemplateRequestDto[] checklisttemplate_dto) {
        for (CheckListTemplateRequestDto checklisttemplate:checklisttemplate_dto) {
            ServiceResponse<CheckListTemplate> serv_resp=delete(authorization,checklisttemplate.getId());
            if(serv_resp.getError()!=null) {
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); //set rollback
                return new ServiceResponse<>(null, serv_resp.getError());
            }
        }
        return  new ServiceResponse<>(checklisttemplate_dto,null);
    }

}
