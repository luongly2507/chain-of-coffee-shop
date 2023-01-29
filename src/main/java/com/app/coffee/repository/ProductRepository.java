package com.app.coffee.repository;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.coffee.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product,UUID>{

    @Query(value = "SELECT * FROM Product p WHERE LOWER(p.name) LIKE %:name%", 
    countQuery = "SELECT count(id) FROM Product p WHERE LOWER(p.name) LIKE %:name%", nativeQuery = true)
    Page<Product> findByName(@Param("name") String name, Pageable pageable);

    boolean existsByName(String name);
    
}
