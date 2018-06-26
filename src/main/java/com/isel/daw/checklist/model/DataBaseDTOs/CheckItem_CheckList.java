package com.isel.daw.checklist.model.DataBaseDTOs;

public class CheckItem_CheckList {

    private CheckItem checkItem;


    private CheckList checkList;

    public CheckItem_CheckList(CheckItem checkItem,CheckList checkList){
        this.checkItem=checkItem;
        this.checkList=checkList;
    }

    public CheckList getCheckList() {
        return checkList;
    }
    public CheckItem getCheckItem() {
        return checkItem;
    }


}
