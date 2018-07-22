package com.isel.daw.checklist.model.RequestsDTO;

public class CheckItemTemplateRequestDto {

    private String name;

    private String description;

    private long id;

    public CheckItemTemplateRequestDto(){}

    public CheckItemTemplateRequestDto(long id,String name,String description){
        this.id=id;
        this.name=name;
        this.description=description;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }




}
