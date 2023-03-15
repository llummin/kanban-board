package com.simbirsoft.kanbanboard.repository;

import com.simbirsoft.kanbanboard.model.Release;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReleaseRepository extends JpaRepository<Release, Long> {

}

