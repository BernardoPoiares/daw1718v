package com.isel.daw.checklist.model.RequestsDTO;

import java.util.List;

public class CheckListTemplateRequestDto {

    private long id;

    private String name;

    private CheckItemRequestDto[] checkitems;

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }


    public CheckItemRequestDto[] getCheckitems() {
        return checkitems;
    }
}