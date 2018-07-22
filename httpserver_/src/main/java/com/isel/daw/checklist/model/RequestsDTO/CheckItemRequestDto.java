package com.isel.daw.checklist.model.RequestsDTO;

import com.isel.daw.checklist.model.DataBaseDTOs.CheckItemTemplate;

import javax.persistence.Column;

public class CheckItemRequestDto {

    private long id;
    private CheckItemTemplateRequestDto checkitemtemplate;
    private String state;

    public CheckItemRequestDto(){}

    public CheckItemRequestDto(long id,long checkitemtemp_id,String name,String description,String state ){
        this.id=id;
        this.checkitemtemplate=new CheckItemTemplateRequestDto(checkitemtemp_id,name,description);
        this.state=state;
    }

    public long getId() {
        return id;
    }

    public CheckItemTemplateRequestDto getCheckitemtemplate() {
        return checkitemtemplate;
    }

    public String getState() {
        return state;
    }
}
