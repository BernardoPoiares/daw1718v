package com.isel.daw.checklist;

import com.isel.daw.checklist.model.DataBaseDTOs.CheckItem;
import com.isel.daw.checklist.model.DataBaseDTOs.CheckItemTemplate;
import com.isel.daw.checklist.model.DataBaseDTOs.CheckList;
import com.isel.daw.checklist.model.DataBaseDTOs.CheckListTemplate;
import com.isel.daw.checklist.model.RequestsDTO.CheckItemRequestDto;
import com.isel.daw.checklist.model.RequestsDTO.CheckItemTemplateRequestDto;
import com.isel.daw.checklist.model.RequestsDTO.CheckListRequestDto;
import com.isel.daw.checklist.model.RequestsDTO.CheckListTemplateRequestDto;

import java.util.List;

public class Converter {
    public static CheckItemRequestDto[] CheckItemsDTO_CheckItems(CheckItem[] checkItems){
        CheckItemRequestDto[] array=new CheckItemRequestDto[checkItems.length];
        for (int i=0;i<checkItems.length;i++){
            CheckItemTemplate checkItemTemplate=checkItems[i].getCheckitem_itemtemplate();
            array[i]=new CheckItemRequestDto(checkItems[i].getId(),checkItemTemplate.getId(),checkItemTemplate.getName(),checkItemTemplate.getDescription(),checkItems[i].getState());
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

    public static CheckItemTemplateRequestDto CheckItemTemplateDto_CheckItemTemplate(CheckItemTemplate checkItemTemplate){
        return new CheckItemTemplateRequestDto(checkItemTemplate.getId(),checkItemTemplate.getName(),checkItemTemplate.getDescription());
    }
    public static CheckItemTemplateRequestDto[] CheckItemsTemplatesDTO_CheckItemsTemplates(List<CheckItemTemplate> checkItemTemplates){
        CheckItemTemplateRequestDto[] array=new CheckItemTemplateRequestDto[checkItemTemplates.size()];
        for (int i=0;i<checkItemTemplates.size();i++){
            array[i]=new CheckItemTemplateRequestDto(checkItemTemplates.get(i).getId(),checkItemTemplates.get(i).getName(),checkItemTemplates.get(i).getDescription());
        }
        return array;
    }

    public static CheckItemRequestDto CheckItemDto_CheckItem(CheckItem checkItem){
        return new CheckItemRequestDto(checkItem.getId(),
                checkItem.getCheckitem_itemtemplate().getId(),
                checkItem.getCheckitem_itemtemplate().getName(),
                checkItem.getCheckitem_itemtemplate().getDescription(),
                checkItem.getState());
    }

    public static CheckListRequestDto CheckListDTO_CheckList(CheckList checkList){
        return new CheckListRequestDto(checkList.getId(),checkList.getName(),checkList.getCompletionDate());
    }

    public static CheckListTemplateRequestDto CheckListTemplateDTO_CheckList(CheckListTemplate checkList){
        return new CheckListTemplateRequestDto(checkList.getId(),checkList.getName());
    }
}
