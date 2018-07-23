package com.isel.daw.checklist.repositories;

import com.isel.daw.checklist.model.DataBaseDTOs.CheckItem;
import com.isel.daw.checklist.model.DataBaseDTOs.CheckItemTemplate;
import com.isel.daw.checklist.model.DataBaseDTOs.CheckList;
import com.isel.daw.checklist.model.DataBaseDTOs.CheckListTemplate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CheckItemTemplateRepository extends JpaRepository<CheckItemTemplate,Integer> {
    CheckItemTemplate findById(long id);
    long deleteById(long id);

    List<CheckItemTemplate> findAllByCheckitemtemplateChecklisttemplate(CheckListTemplate checkListTemplate);


}
