package com.isel.daw.checklist.repositories;

import com.isel.daw.checklist.model.DataBaseDTOs.CheckList;
import com.isel.daw.checklist.model.DataBaseDTOs.CheckListTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CheckListTemplateRepository extends JpaRepository<CheckListTemplate,Integer> {

    CheckListTemplate findById(long id);
    long deleteById(long id);

    @Query(value="SELECT * FROM checklist_template WHERE listtemplate_user = :id AND name=:name", nativeQuery = true)
    List<CheckListTemplate> searchByName(@Param("id")long id, @Param("name")String name);

}
