package com.app.coffee.repository;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.coffee.entity.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, UUID> {

    boolean existsByName(String name);

    @Query(value = "SELECT * FROM Category c WHERE LOWER(c.name) LIKE %:name%", 
    countQuery = "SELECT count(id) FROM Category c WHERE LOWER(c.name) LIKE %:name%", nativeQuery = true)
    Page<Category> findByName(@Param("name") String name, Pageable pageable);

}
