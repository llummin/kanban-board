package com.simbirsoft.kanbanboard.repository;

import com.simbirsoft.kanbanboard.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}

