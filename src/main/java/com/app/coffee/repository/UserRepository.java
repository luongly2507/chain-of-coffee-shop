package com.app.coffee.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.coffee.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User,UUID>{
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
    boolean existsByTelephone(String telephone);
    @Query(value = "SELECT * FROM Users u WHERE LOWER(u.email) LIKE %:key%", 
    countQuery = "SELECT count(id) FROM Users u WHERE LOWER(u.email) LIKE %:key%", nativeQuery = true)
    Page<User> findByEmail(@Param("key") String key, Pageable pageable);
    
    @Query(nativeQuery = true,
    value = "SELECT u.id, u.name, u.address, u.telephone,u.gender, u.email, u.password, u.birthday, u.branch_id, u.created_at, u.last_modified_at, u.created_by, u.last_modified_by FROM users u LEFT JOIN users_roles d ON u.id = d.users_id LEFT JOIN role r ON r.id = d.role_id WHERE r.name <> 'ROLE_ADMIN' AND (u.email LIKE %:key% OR u.telephone LIKE %:key% OR LOWER(u.name) LIKE %:key%)", 
    countQuery = "SELECT count(u.id) FROM users u LEFT JOIN users_roles d ON u.id = d.users_id LEFT JOIN role r ON r.id = d.role_id WHERE r.name <> 'ROLE_ADMIN' AND (u.email  LIKE %:key% OR u.telephone  LIKE %:key% OR LOWER(u.name) LIKE %:key%) ")

    Page<User> searchAllByAdmin(@Param("key") String key, Pageable pageable);
   
    @Query(nativeQuery = true,
    value = "SELECT u.id, u.name, u.address, u.telephone,u.gender, u.email, u.password, u.birthday, u.branch_id, u.created_at, u.last_modified_at, u.created_by, u.last_modified_by FROM users u LEFT JOIN users_roles d ON u.id = d.users_id LEFT JOIN role r ON r.id = d.role_id WHERE r.name <> 'ROLE_ADMIN' AND r.name <> 'ROLE_MANAGER' AND  u.branch_id = :branch AND LOWER(u.name) LIKE %:key% OR u.email LIKE %:key% OR u.telephone LIKE %:key%", 
    countQuery = "SELECT count(u.id) FROM users u LEFT JOIN users_roles d ON u.id = d.users_id LEFT JOIN role r ON r.id = d.role_id WHERE r.name <> 'ROLE_ADMIN' AND r.name <> 'ROLE_MANAGER' AND  u.branch_id = :branch AND LOWER(u.name) LIKE %:key% OR u.email LIKE %:key% OR u.telephone LIKE %:key%")

    Page<User> searchAllByManager(@Param("key") String key, @Param("branch") UUID branch, Pageable pageable);
    @Query(nativeQuery = true,
    value = "SELECT u.id, u.name, u.address, u.telephone,u.gender, u.email, u.password, u.birthday, u.branch_id, u.created_at, u.last_modified_at, u.created_by, u.last_modified_by FROM users u LEFT JOIN users_roles d ON u.id = d.users_id LEFT JOIN role r ON r.id = d.role_id WHERE r.name <> 'ROLE_ADMIN'", 
    countQuery = "SELECT count(u.id) FROM users u LEFT JOIN users_roles d ON u.id = d.users_id LEFT JOIN role r ON r.id = d.role_id WHERE r.name <> 'ROLE_ADMIN' ")

    Page<User> findAllByAdmin(Pageable pageable);
   
    @Query(nativeQuery = true,
    value = "SELECT u.id, u.name, u.address, u.telephone,u.gender, u.email, u.password, u.birthday, u.branch_id, u.created_at, u.last_modified_at, u.created_by, u.last_modified_by FROM users u LEFT JOIN users_roles d ON u.id = d.users_id LEFT JOIN role r ON r.id = d.role_id WHERE r.name <> 'ROLE_ADMIN' AND r.name <> 'ROLE_MANAGER' AND  u.branch_id = :branch ", 
    countQuery = "SELECT count(u.id) FROM users u LEFT JOIN users_roles d ON u.id = d.users_id LEFT JOIN role r ON r.id = d.role_id WHERE r.name <> 'ROLE_ADMIN' AND r.name <> 'ROLE_MANAGER' AND  u.branch_id = :branch")

    Page<User> findAllByManager(@Param("branch") UUID branch, Pageable pageable);
}
