package com.isel.daw.checklist.model;


import com.isel.daw.checklist.model.Users;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name="checkitem_template")
public class CheckItemTemplate implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private long id;

    @Column(name="name")
    private String name;

    @Column(name="description")
    private String description;

    @ManyToOne
    @JoinColumn(name="itemtemplate_user")
    private Users itemtemplate_user;


    @OneToMany(mappedBy="checkitem_itemtemplate")
    private Set<CheckItem> checkItems;


    @ManyToOne
    @JoinColumn(name="checkitemtemplate_checklisttemplate")
    private CheckListTemplate checkitemtemplate_checklisttemplate;

    protected CheckItemTemplate() {}

    public CheckItemTemplate(String name,Users itemtemplate_user){
        this.name = name;
        this.itemtemplate_user = itemtemplate_user;
    }

    public CheckItemTemplate(String name,String description,Users itemtemplate_user){
        this.name = name;
        this.description = description;
        this.itemtemplate_user = itemtemplate_user;
    }

/*
    public Set<CheckItem> getCheckItems() {
        return checkItems;
    }

    public void setCheckItems(Set<CheckItem> checkItems) {
        this.checkItems = checkItems;
    }
*/
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Users getItemTemplate_user() {
        return itemtemplate_user;
    }

    public void setItemTemplate_user(Users itemtemplate_user) {
        this.itemtemplate_user = itemtemplate_user;
    }

    public Set<CheckItem> getCheckItems() {
        return checkItems;
    }

    public void setCheckItems(Set<CheckItem> checkItems) {
        this.checkItems = checkItems;
    }

    public CheckListTemplate getCheckitemtemplate_checklisttemplate() {
        return checkitemtemplate_checklisttemplate;
    }

    public void setCheckitemtemplate_checklisttemplate(CheckListTemplate checkListTemplate) {
        this.checkitemtemplate_checklisttemplate = checkListTemplate;
    }
/*
    public List<CheckItem> getCheckLists() {
        return checkLists;
    }

    public void setCheckLists(List<CheckItem> checkLists) {
        this.checkLists = checkLists;
    }*/
}
