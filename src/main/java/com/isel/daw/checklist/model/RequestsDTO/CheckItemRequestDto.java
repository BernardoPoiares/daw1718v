package com.isel.daw.checklist.model.RequestsDTO;

import javax.persistence.Column;

public class CheckItemRequestDto {

    private int id;

    private String name;

    private String description;

    private String state;



    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getId() {
        return id;
    }

    public String getState() {
        return state;
    }
}
