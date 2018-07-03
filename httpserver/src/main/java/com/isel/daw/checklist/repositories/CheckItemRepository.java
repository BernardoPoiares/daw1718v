package com.isel.daw.checklist.repositories;


import com.isel.daw.checklist.model.DataBaseDTOs.CheckItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface CheckItemRepository extends JpaRepository<CheckItem, Integer> {
    CheckItem findById(long id);
    long deleteById(long id);

    @Query(value="SELECT COUNT(*) FROM checkitem WHERE checkitem_itemtemplate = :id", nativeQuery = true)
    long countByTemplateId(@Param("id")long id);
}
