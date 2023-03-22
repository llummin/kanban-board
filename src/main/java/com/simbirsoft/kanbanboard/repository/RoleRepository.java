package com.simbirsoft.kanbanboard.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.simbirsoft.kanbanboard.model.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

}

