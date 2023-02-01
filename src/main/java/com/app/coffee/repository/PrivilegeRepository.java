package com.app.coffee.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.coffee.entity.Privilege;

@Repository
public interface PrivilegeRepository extends JpaRepository<Privilege, UUID>{
    Privilege findByName(String name);
}
