package com.isel.daw.checklist.repositories;

import com.isel.daw.checklist.model.CheckListTemplate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CheckListTemplateRepository extends JpaRepository<CheckListTemplate,Integer> {
}
