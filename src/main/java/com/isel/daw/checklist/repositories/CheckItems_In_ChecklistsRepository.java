package com.isel.daw.checklist.repositories;

import com.isel.daw.checklist.model.DataBaseDTOs.CheckItem;
import com.isel.daw.checklist.model.DataBaseDTOs.CheckItemTemplate;
import com.isel.daw.checklist.model.DataBaseDTOs.CheckItem_CheckList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CheckItems_In_ChecklistsRepository extends JpaRepository<CheckItem_CheckList,Integer> {
    List<CheckItem> findByCheckList_Id(long id);
}
