package com.isel.daw.checklist.model.RequestsDTO;

import com.isel.daw.checklist.model.DataBaseDTOs.CheckItemTemplate;

import javax.persistence.Column;

public class CheckItemRequestDto {

    private int id;
    private CheckItemTemplateRequestDto checkitemtemplate;
    private String state;

    public int getId() {
        return id;
    }

    public CheckItemTemplateRequestDto getCheckitemtemplate() {
        return checkitemtemplate;
    }

    public String getState() {
        return state;
    }
}
