package com.app.coffee.repository;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.coffee.entity.Branch;

@Repository
public interface BranchRepository extends JpaRepository<Branch, UUID> {

    boolean existsByName(String name);

    @Query(value = "SELECT * FROM Branch b WHERE LOWER(b.name) LIKE %:name%", 
    countQuery = "SELECT count(id) FROM Branch b WHERE LOWER(b.name) LIKE %:name%", nativeQuery = true)
    Page<Branch> findByName(@Param("name") String name, Pageable pageable);
}
