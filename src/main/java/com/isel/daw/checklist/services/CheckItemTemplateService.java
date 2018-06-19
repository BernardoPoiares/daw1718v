package com.isel.daw.checklist.services;

import com.isel.daw.checklist.model.CheckItemTemplate;
import com.isel.daw.checklist.Service;
import com.isel.daw.checklist.repositories.CheckItemTemplateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CheckItemTemplateService implements Service {

    private final CheckItemTemplateRepository itemTemplateRepository;

    @Autowired
    public CheckItemTemplateService(CheckItemTemplateRepository itemTemplateRepository) {
        this.itemTemplateRepository = itemTemplateRepository;
    }


    public ResponseEntity<?> getAll() {
        List<CheckItemTemplate> res = itemTemplateRepository.findAll();
        return new ResponseEntity<>(
                res.size(),
                HttpStatus.ACCEPTED
        );
    }

}