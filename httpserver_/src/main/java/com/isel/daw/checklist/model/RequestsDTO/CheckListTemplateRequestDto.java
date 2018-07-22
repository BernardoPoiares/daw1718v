package com.isel.daw.checklist.model.RequestsDTO;

import java.util.List;

public class CheckListTemplateRequestDto {

    private long id;

    private String name;

    public CheckListTemplateRequestDto(){}


    public CheckListTemplateRequestDto(long id, String name){
        this.id=id;
        this.name=name;
    }

    private CheckItemTemplateRequestDto[] checkitemstemplates;

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public CheckItemTemplateRequestDto[] getCheckitemstemplates() {
        return checkitemstemplates;
    }
}
