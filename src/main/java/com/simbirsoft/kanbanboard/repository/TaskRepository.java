package com.simbirsoft.kanbanboard.repository;

import com.simbirsoft.kanbanboard.model.*;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
  List<Task> findByProject(Project project);
}
