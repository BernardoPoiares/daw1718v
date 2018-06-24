package com.isel.daw.checklist.repositories;

import com.isel.daw.checklist.model.CheckList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CheckListRepository extends JpaRepository<CheckList,Integer>{
    CheckList findById(long id);
    long deleteById(long id);
}
