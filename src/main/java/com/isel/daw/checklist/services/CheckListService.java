package com.isel.daw.checklist.services;

import com.isel.daw.checklist.model.CheckItemTemplate;
import com.isel.daw.checklist.model.CheckList;
import com.isel.daw.checklist.repositories.CheckListRepository;
import org.hibernate.exception.DataException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class CheckListService {

    private final CheckListRepository checkListRepository;

    @Autowired
    public CheckListService(CheckListRepository checkListRepository){
        this.checkListRepository=checkListRepository;
    }

    public ResponseEntity<?> getById(int id){
        CheckList res=checkListRepository.findById(id);
        return new ResponseEntity<>(
                res,
                HttpStatus.ACCEPTED
        );
    }

    public ResponseEntity<?> create(String name, Date completionDate){
       // CheckList res=checkListRepository.save(id);
        return new ResponseEntity<>(
                null,
                HttpStatus.ACCEPTED
        );
    }
}
