package com.isel.daw.checklist;

import com.isel.daw.checklist.model.DataBaseDTOs.CheckItem;
import com.isel.daw.checklist.model.DataBaseDTOs.CheckList;
import com.isel.daw.checklist.model.DataBaseDTOs.CheckListTemplate;
import com.isel.daw.checklist.model.RequestsDTO.CheckItemRequestDto;
import com.isel.daw.checklist.model.RequestsDTO.CheckListRequestDto;
import com.isel.daw.checklist.model.RequestsDTO.CheckListTemplateRequestDto;

public class Converter {
    public static CheckItemRequestDto[] CheckItemsDTO_CheckItems(CheckItem[] checkItems){
        CheckItemRequestDto[] array=new CheckItemRequestDto[checkItems.length];
        for (int i=0;i<checkItems.length;i++){
            array[i]=new CheckItemRequestDto(checkItems[i].getId(),checkItems[i].getCheckitem_itemtemplate().getName(),checkItems[i].getCheckitem_itemtemplate().getDescription(),checkItems[i].getState());
        }
        return array;
    }

    public  static CheckListRequestDto[] CheckListsDTO_CheckLists(CheckList[] checkLists){
        CheckListRequestDto[] array=new CheckListRequestDto[checkLists.length];
        for (int i=0;i<checkLists.length;i++){
            array[i]=new CheckListRequestDto(checkLists[i].getId(),checkLists[i].getName(),checkLists[i].getCompletionDate());
        }
        return array;
    }

    public  static CheckListTemplateRequestDto[] CheckListsTemplateDTO_CheckListsTemplate(CheckListTemplate[] checkListsTemplates){
        CheckListTemplateRequestDto[] array=new CheckListTemplateRequestDto[checkListsTemplates.length];
        for (int i=0;i<checkListsTemplates.length;i++){
            array[i]=new CheckListTemplateRequestDto(checkListsTemplates[i].getId(),checkListsTemplates[i].getName());
        }
        return array;
    }
}
