package com.isel.daw.checklist.repositories;


import com.isel.daw.checklist.model.CheckItem;
import com.isel.daw.checklist.model.CheckList;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CheckItemRepository extends JpaRepository<CheckItem, Integer> {
    CheckItem findById(long id);
}
