package com.simbirsoft.kanbanboard.repository;

import com.simbirsoft.kanbanboard.model.Project;
import com.simbirsoft.kanbanboard.model.Release;
import com.simbirsoft.kanbanboard.model.Task;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReleaseRepository extends JpaRepository<Release, Long> {
  List<Release> findByTask(Task task);
}

