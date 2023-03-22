package com.simbirsoft.kanbanboard.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.simbirsoft.kanbanboard.model.*;
import java.util.List;

@Repository
public interface ReleaseRepository extends JpaRepository<Release, Long> {
  List<Release> findByTask(Task task);
}