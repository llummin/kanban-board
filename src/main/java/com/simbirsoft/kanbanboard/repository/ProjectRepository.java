package com.simbirsoft.kanbanboard.repository;

import com.simbirsoft.kanbanboard.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Long> {
  // TODO: добавить методы для работы с проектами
}
