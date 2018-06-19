package com.isel.daw.checklist.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name="checkitem")
public class CheckItem implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private long id;

    @Column(name="state")
    private String state;

    @ManyToOne
    @JoinColumn(name="checkitem_itemtemplate")
    private CheckItemTemplate checkitem_itemtemplate;


    @ManyToMany(mappedBy="checkItems")
    private Set<CheckList> checkLists;

    protected CheckItem() {}

    public CheckItem(String state, CheckItemTemplate checkitem_itemtemplate){
        this.state = state;
        this.checkitem_itemtemplate = checkitem_itemtemplate;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public CheckItemTemplate getCheckitem_itemtemplate() {
        return checkitem_itemtemplate;
    }

    public void setCheckitem_itemtemplate(CheckItemTemplate checklist_listtemplate) {
        this.checkitem_itemtemplate = checklist_listtemplate;
    }

    public Set<CheckList> getCheckLists() {
        return checkLists;
    }

    public void setCheckLists(Set<CheckList> checkLists) {
        this.checkLists = checkLists;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
/*
    public int getCheckItemTemplateId() {
        return checkItemTemplateId;
    }

    public void setCheckItemTemplateId(int listID) {
        this.checkItemTemplateId = checkItemTemplateId;
    }

    public Set<CheckList> getCheckLists() {
        return checkLists;
    }

    public void setCheckLists(Set<CheckList> checkLists) {
        this.checkLists = checkLists;
    }*/
}
