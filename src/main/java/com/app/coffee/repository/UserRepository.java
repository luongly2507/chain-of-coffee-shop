package com.app.coffee.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.coffee.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User,UUID>{
    User findByEmail(String email);
    boolean existsByEmail(String email);
    boolean existsByTelephone(String telephone);
}
