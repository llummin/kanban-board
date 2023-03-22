package com.simbirsoft.kanbanboard.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.simbirsoft.kanbanboard.model.Project;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

}
