package com.isel.daw.checklist.services;

import com.isel.daw.checklist.Service;
import com.isel.daw.checklist.ServiceResponse;
import com.isel.daw.checklist.ValidatorResponse;
import com.isel.daw.checklist.model.DataBaseDTOs.CheckItemTemplate;
import com.isel.daw.checklist.model.DataBaseDTOs.CheckListTemplate;
import com.isel.daw.checklist.model.DataBaseDTOs.Users;
import com.isel.daw.checklist.model.RequestsDTO.CheckItemTemplateRequestDto;
import com.isel.daw.checklist.model.RequestsDTO.CheckListTemplateRequestDto;
import com.isel.daw.checklist.model.Validators.CheckItemTemplateValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class CheckItemTemplate_CheckListTemplateService {

    private final CheckListTemplateService checkListTemplateService;

    private final CheckItemTemplateService checkItemTemplateService;

    @Autowired
    public CheckItemTemplate_CheckListTemplateService(CheckListTemplateService checkListTemplateService,CheckItemTemplateService checkItemTemplateService){
        this.checkItemTemplateService=checkItemTemplateService;
        this.checkListTemplateService=checkListTemplateService;
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
}
