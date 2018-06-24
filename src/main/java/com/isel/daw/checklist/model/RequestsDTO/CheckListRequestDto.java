package com.isel.daw.checklist.model.RequestsDTO;

import com.isel.daw.checklist.model.CheckListTemplate;
import com.isel.daw.checklist.model.Users;

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


}
