package com.app.coffee.repository;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.coffee.entity.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, UUID>{
    boolean existsByName(String name);

    @Query(value = "SELECT * FROM Customer c WHERE LOWER(c.name) LIKE %:key% OR c.telephone LIKE %:key%", 
    countQuery = "SELECT count(id) FROM Customer c WHERE LOWER(c.name) LIKE %:key% OR  c.telephone LIKE %:key%", nativeQuery = true)
    Page<Customer> findByNameAndTelephone(@Param("key") String key, Pageable pageable);

}
 