package com.isel.daw.checklist.model.RequestsDTO;

public class CheckItemTemplateRequestDto {

    private String name;

    private String description;

    private int id;

    public CheckItemTemplateRequestDto(){}

    public CheckItemTemplateRequestDto(String name,String description){
        this.name=name;
        this.description=description;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }




}
