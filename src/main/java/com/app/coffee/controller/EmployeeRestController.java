package com.app.coffee.controller;

import java.net.URI;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.app.coffee.payload.request.CreateUserRequest;
import com.app.coffee.payload.request.UpdateUserRequest;
import com.app.coffee.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/employees")
@PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
public class EmployeeRestController {
    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<?> getAllEmployee(@RequestParam List<String> roles, @RequestParam String branchId,
            Pageable pageable) {
        return ResponseEntity.ok(userService.getAllUsers(roles, branchId, pageable));
    }
    @GetMapping("/{employeeId}")
    public ResponseEntity<?> getEmployeeById(@PathVariable UUID employeeId) {
        return ResponseEntity.ok(userService.getUserById(employeeId));
    }
    @PostMapping
    public ResponseEntity<?> createEmployee(@RequestBody @Valid CreateUserRequest createUserRequest) {
        System.out.println(createUserRequest);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(userService.createUser(createUserRequest).getId()).toUri();
        return ResponseEntity.created(location).build();
    }
    @PutMapping("/{employeeId}")
    public ResponseEntity<?> updateEmployeeById(@PathVariable UUID employeeId, @RequestBody @Valid UpdateUserRequest updateUserRequest) {
        userService.updateUser(employeeId, updateUserRequest);
        return ResponseEntity.noContent().build();
    }
    @DeleteMapping("/{employeeId}")
    public ResponseEntity<?> deleteEmployee(@PathVariable UUID employeeId) {
        userService.deleteUser(employeeId);
        return ResponseEntity.noContent().build();
    }
   
}
