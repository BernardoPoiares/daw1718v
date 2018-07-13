package com.isel.daw.checklist.repositories;

import com.isel.daw.checklist.model.DataBaseDTOs.CheckList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CheckListRepository extends JpaRepository<CheckList,Integer>{
    CheckList findById(long id);
    long deleteById(long id);

    @Query(value="SELECT * FROM checklist WHERE list_user = :id", nativeQuery = true)
    CheckList[] findAllbyUser(long id);
}
