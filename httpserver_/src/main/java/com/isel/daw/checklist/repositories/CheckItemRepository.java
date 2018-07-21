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

    @Query(value="SELECT * FROM checkitem C inner join checkitem_template Ct on C.checkitem_itemtemplate= Ct.id WHERE ct.itemtemplate_user = :id", nativeQuery = true)
    CheckItem[] findAllbyUser(@Param("id")long id);

    @Query(value="SELECT * FROM checkitem c inner join checkitem_template Ct on c.checkitem_itemtemplate= Ct.id WHERE ct.itemtemplate_user = :id AND ct.name=:name", nativeQuery = true)
    CheckItem[] searchByName(@Param("id")long id,@Param("name")String name);

    @Query(value="\tSELECT * FROM checkitem c inner join checkitem_template Ct on c.checkitem_itemtemplate= Ct.id inner join checkitems_in_checklists ciscl on ciscl.checkitem_id= c.id WHERE ct.itemtemplate_user =:user_id AND ciscl.checklist_id=:list_id", nativeQuery = true)
    CheckItem[] searchByList(@Param("user_id")long user_id,@Param("list_id")long list_id);


}
