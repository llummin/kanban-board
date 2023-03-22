package com.simbirsoft.kanbanboard.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.simbirsoft.kanbanboard.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}

