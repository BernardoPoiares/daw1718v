package com.isel.daw.checklist.model;

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

    @OneToMany
    @JoinColumn(name="checklist_listtemplate")
    private Set<CheckList> checklist_listtemplate;

    @ManyToMany
    @JoinTable(name = "templateitems_in_templatelist",
            joinColumns = { @JoinColumn(name = "listtemplate_id")  },
            inverseJoinColumns = { @JoinColumn(name = "itemtemplate_id")})
    private Set<CheckItemTemplate> itemsTemplates ;


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

    public Set<CheckList> getChecklist_listtemplate() {
        return checklist_listtemplate;
    }

    public void setChecklist_listtemplate(Set<CheckList> checklist_listtemplate) {
        this.checklist_listtemplate = checklist_listtemplate;
    }



    public Set<CheckItemTemplate> getItemsTemplates() {
        return itemsTemplates;
    }

    public void setItemsTemplates(Set<CheckItemTemplate> itemsTemplates) {
        this.itemsTemplates = itemsTemplates;
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
