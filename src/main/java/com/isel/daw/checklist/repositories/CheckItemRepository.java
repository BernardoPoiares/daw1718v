package com.isel.daw.checklist.repositories;


import com.isel.daw.checklist.model.CheckItem;
import com.isel.daw.checklist.model.CheckList;
import com.isel.daw.checklist.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface CheckItemRepository extends JpaRepository<CheckItem, Integer> {
    CheckItem findById(long id);
    CheckItem deleteById(long id);

    @Query(value="SELECT * FROM checkitem WHERE checkitem_itemtemplate= :id", nativeQuery = true)
    long countByTemplateId(long id);
}
