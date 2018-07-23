package com.isel.daw.checklist.model.RequestsDTO;

public class CheckItem_CheckListsRequestDto {

    private long id;

    private CheckListRequestDto[] checklists;

    public long getId() {
        return id;
    }


    public CheckListRequestDto[] getChecklists() {
        return checklists;
    }

}