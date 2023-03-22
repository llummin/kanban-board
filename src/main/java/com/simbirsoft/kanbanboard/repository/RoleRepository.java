package com.simbirsoft.kanbanboard.repository;

import com.simbirsoft.kanbanboard.model.Role;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

}
