package com.alterra.miniproject.repository;

import com.alterra.miniproject.domain.dao.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> getDistinctTopByUsername(String username);
}

