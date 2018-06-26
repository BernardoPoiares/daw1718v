package com.isel.daw.checklist.model.RequestsDTO;

import com.isel.daw.checklist.model.DataBaseDTOs.CheckItem;
import com.isel.daw.checklist.model.DataBaseDTOs.CheckListTemplate;

import java.util.Date;

public class CheckItem_CheckListRequestDto {


    private long id;

    private CheckItemRequestDto[] checkitems;

    public long getId() {
        return id;
    }


    public CheckItemRequestDto[] getCheckitems() {
        return checkitems;
    }

}
