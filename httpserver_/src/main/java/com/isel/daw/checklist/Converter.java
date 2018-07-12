package com.isel.daw.checklist;

import com.isel.daw.checklist.model.DataBaseDTOs.CheckItem;
import com.isel.daw.checklist.model.RequestsDTO.CheckItemRequestDto;

public class Converter {
    public static CheckItemRequestDto[] CheckItemsDTO_CheckItems(CheckItem[] checkItems){
        CheckItemRequestDto[] array=new CheckItemRequestDto[checkItems.length];
        for (int i=0;i<checkItems.length;i++){
            array[i]=new CheckItemRequestDto(checkItems[i].getId(),checkItems[i].getCheckitem_itemtemplate().getName(),checkItems[i].getCheckitem_itemtemplate().getDescription(),checkItems[i].getState());
        }
        return array;
    }
}
