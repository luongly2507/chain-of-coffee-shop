package com.app.coffee.repository;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.coffee.entity.Tag;

@Repository
public interface TagRepository extends JpaRepository<Tag, UUID>{
    boolean existsByName(String name);

    @Query(value = "SELECT * FROM Tag t WHERE LOWER(t.name) LIKE %:key%", 
    countQuery = "SELECT count(id) FROM Tag t WHERE LOWER(t.name) LIKE %:key%", nativeQuery = true)
    Page<Tag> findByName(@Param("key") String key, Pageable pageable);
}
