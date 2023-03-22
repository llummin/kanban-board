package com.simbirsoft.kanbanboard.repository;

import com.simbirsoft.kanbanboard.model.Project;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

}
