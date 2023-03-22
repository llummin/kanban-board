package com.simbirsoft.kanbanboard.repository;

import com.simbirsoft.kanbanboard.model.Task;
import com.simbirsoft.kanbanboard.model.Release;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReleaseRepository extends JpaRepository<Release, Long> {
  List<Release> findByTask(Task task);
}
