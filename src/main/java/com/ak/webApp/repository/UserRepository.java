package com.ak.webApp.repository;

import com.ak.webApp.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUsername(String username);

    User findFirstByOrderByUserIdDesc();

    Optional<User> findByUserId(int index);
}
