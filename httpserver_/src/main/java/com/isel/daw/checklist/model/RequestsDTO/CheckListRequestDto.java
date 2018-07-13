package com.isel.daw.checklist.model.RequestsDTO;

import com.isel.daw.checklist.model.DataBaseDTOs.CheckListTemplate;

import java.util.Date;

public class CheckListRequestDto {


    private long id;

    private String name;

    private Date completionDate;

    private CheckListTemplate checkListTemplate;

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Date getCompletionDate() {
        return completionDate;
    }

    public CheckListTemplate getCheckListTemplate() {
        return checkListTemplate;
    }

    public CheckListRequestDto(){}

    public CheckListRequestDto(long id,String name,Date completionDate){
        this.id=id;
        this.name=name;
        this.completionDate=completionDate;
    }

    public void setCheckListTemplate(CheckListTemplate checkListTemplate) {
        this.checkListTemplate = checkListTemplate;
    }

}
