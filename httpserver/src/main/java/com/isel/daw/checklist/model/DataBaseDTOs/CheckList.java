package com.isel.daw.checklist.model.DataBaseDTOs;


import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

@Entity
@Table(name = "checklist")
public class CheckList implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "completionDate")
    private Date completionDate;

    @ManyToOne
    @JoinColumn(name="list_user")
    private Users list_user;

    @ManyToOne
    @JoinColumn(name="checklist_checklisttemplate")
    private CheckListTemplate checklist_checklisttemplate;

    @ManyToMany
    @JoinTable(name = "checkitems_in_checklists",
            joinColumns = { @JoinColumn(name = "checklist_id")  },
            inverseJoinColumns = { @JoinColumn(name = "checkitem_id")})
    private Set<CheckItem> checkItems ;


    protected CheckList(){}

    public CheckList(String name, Users list_user){
        this.name = name;
        this.list_user = list_user;
    }


    public CheckList(String name, Users list_user,Date completionDate){
        this.name = name;
        this.list_user = list_user;
        this.completionDate=completionDate;
    }

    public CheckList(String name, Users list_user,Date completionDate,CheckListTemplate checkListTemplate){
        this.name = name;
        this.list_user = list_user;
        this.completionDate=completionDate;
        this.checklist_checklisttemplate=checkListTemplate;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCompletionDate() {
        return completionDate;
    }

    public void setCompletionDate(Date completionDate) {
        this.completionDate = completionDate;
    }


    public Users getList_user() {
        return list_user;
    }

    public void setList_user(Users username) {
        this.list_user = list_user;
    }

    public Set<CheckItem> getCheckItems() {
        return checkItems;
    }

    public void setCheckItems(Set<CheckItem> checkItems) {
        this.checkItems = checkItems;
    }

    public void addCheckItems(CheckItem checkItem){
        this.checkItems.add(checkItem);
    }

    public void removeCheckItems(CheckItem checkItem){
        this.checkItems.remove(checkItem);
    }



/*
    public Set<CheckItem> getCheckItems() {
        return checkItems;
    }

    public void setCheckItems(Set<CheckItem> checkItems) {
        this.checkItems = checkItems;
    }
*//*
    public CheckListTemplate getCheckListTemplate() {
        return checkListTemplate;
    }

    public void setCheckListTemplate(CheckListTemplate checkListTemplate) {
        this.checkListTemplate = checkListTemplate;
    }*/
}
