package com.isel.daw.checklist.repositories;

import com.isel.daw.checklist.model.DataBaseDTOs.CheckItemTemplate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CheckItemTemplateRepository extends JpaRepository<CheckItemTemplate,Integer> {
    CheckItemTemplate findById(long id);
    long deleteById(long id);
}
