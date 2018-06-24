package com.isel.daw.checklist.model;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "users")
public class Users implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private long id;

    @Column(unique = true,nullable=false)
    private String username;

    @Column(nullable=false)
    private String password;
    @OneToMany(mappedBy = "itemtemplate_user")
    private Set<CheckItemTemplate> checkItemsTemplates;

    @Column(nullable=false)
    private String token;

    @OneToMany(mappedBy = "listtemplate_user")
    private Set<CheckListTemplate> checkListTemplates;

    @OneToMany(mappedBy = "list_user")
    private Set<CheckList> checkLists;

    protected Users(){}

    public Users(String username, String password,String token){
        this.username = username;
        this.password = password;
        this.token = token;
    }

    public String getUserName() {
        return username;
    }

    public void setUserName(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<CheckItemTemplate> getCheckItemsTemplates() {
        return checkItemsTemplates;
    }

    public void setCheckItemsTemplates(Set<CheckItemTemplate> checkItemsTemplates) {
        this.checkItemsTemplates = checkItemsTemplates;
    }

    public Set<CheckListTemplate> getCheckListTemplates() {
        return checkListTemplates;
    }

    public void setCheckListTemplates(Set<CheckListTemplate> checkListTemplates) {
        this.checkListTemplates = checkListTemplates;
    }

    public Set<CheckList> getCheckLists() {
        return checkLists;
    }

    public void setCheckLists(Set<CheckList> checkLists) {
        this.checkLists = checkLists;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }


    public long getId() {
        return id;
    }

/*
    public Set<CheckListTemplate> getCheckListTemplates() {
        return checkListTemplates;
    }

    public void setCheckListTemplates(Set<CheckListTemplate> checkListTemplates) {
        this.checkListTemplates = checkListTemplates;
    }

    public Set<CheckList> getCheckLists() {
        return checkLists;
    }

    public void setCheckLists(Set<CheckList> checkLists) {
        this.checkLists = checkLists;
    }*/
/*
    public Set<CheckList> getCheckListIds() {
        return checkListIds;
    }

    public void setCheckListIds(Set<CheckList> checkListIds) {
        this.checkListIds = checkListIds;
    }
*/


}
