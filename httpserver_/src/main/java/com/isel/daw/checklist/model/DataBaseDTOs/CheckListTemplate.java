package com.isel.daw.checklist.model.DataBaseDTOs;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "checklist_template")
public class CheckListTemplate implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private long id;

    @Column(name = "name")
    private String name;

    @ManyToOne
    @JoinColumn(name="listtemplate_user")
    private Users listtemplate_user;

    @OneToMany(mappedBy="checklist_checklisttemplate")
    private Set<CheckList> checklist_checklisttemplate;

    /*@ManyToMany
    @JoinTable(name = "templateitems_in_templatelist",
            joinColumns = { @JoinColumn(name = "listtemplate_id")  },
            inverseJoinColumns = { @JoinColumn(name = "itemtemplate_id")})*/
    @OneToMany(mappedBy="checkitemtemplate_checklisttemplate")
    private Set<CheckItemTemplate> checkitemtemplate_checklisttemplate ;


    protected CheckListTemplate(){}

    public CheckListTemplate(String name,Users listtemplate_user){

        this.name = name;
        this.listtemplate_user = listtemplate_user;
    }

    /*

    public Set<CheckList> getCheckLists() {
        return checkLists;
    }

    public void setCheckLists(Set<CheckList> checkLists) {
        this.checkLists = checkLists;
    }
*/

    public long getId() {
        return id;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
/*
    public Set<CheckListTemplate> getItensTemplates() {
        return itensTemplates;
    }

    public void setItensTemplates(Set<CheckListTemplate> itensTemplates) {
        this.itensTemplates = itensTemplates;
    }*/

    public Users getListtemplate_user() {
        return listtemplate_user;
    }

    public void setListtemplate_user(Users listtemplate_user) {
        this.listtemplate_user = listtemplate_user;
    }

    public Set<CheckList> getChecklist_checklisttemplate() {
        return checklist_checklisttemplate;
    }

    public void setChecklist_checklisttemplate(Set<CheckList> checklist_listtemplate) {
        this.checklist_checklisttemplate = checklist_listtemplate;
    }




    public Set<CheckItemTemplate> getCheckitemtemplate_checklisttemplate() {
        return checkitemtemplate_checklisttemplate;
    }

    public void setCheckitemtemplate_checklisttemplate(Set<CheckItemTemplate> checkitemtemplate_checklisttemplate) {
        this.checkitemtemplate_checklisttemplate = checkitemtemplate_checklisttemplate;
    }

    public void addItemsTemplates(CheckItemTemplate itemTemplate){
        this.checkitemtemplate_checklisttemplate.add(itemTemplate);
    }
    /*
    public Set<CheckListTemplate> getItensTemplates() {
        return itensTemplates;
    }

    public void setItensTemplates(Set<CheckListTemplate> itensTemplates) {
        this.itensTemplates = itensTemplates;
    }*/
/*
    public Set<CheckListTemplate> getItemtemplate_listtemplate() {
        return itemtemplate_listtemplate;
    }

    public void setItemtemplate_listtemplate(Set<CheckListTemplate> itemtemplate_listtemplate) {
        this.itemtemplate_listtemplate = itemtemplate_listtemplate;
    }*/
}