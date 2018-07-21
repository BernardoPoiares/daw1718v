package com.isel.daw.checklist.repositories;

import com.isel.daw.checklist.model.DataBaseDTOs.CheckItem;
import com.isel.daw.checklist.model.DataBaseDTOs.CheckList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CheckListRepository extends JpaRepository<CheckList,Integer>{
    CheckList findById(long id);
    long deleteById(long id);

    @Query(value="SELECT * FROM checklist WHERE list_user = :id", nativeQuery = true)
    CheckList[] findAllbyUser(@Param("id") long id);

    @Query(value="SELECT * FROM checklist WHERE list_user = :id AND name=:name", nativeQuery = true)
    CheckList[] searchByName(@Param("id")long id, @Param("name")String name);

    List<CheckList> findAllByCheckItems(CheckItem checkItem);
}
