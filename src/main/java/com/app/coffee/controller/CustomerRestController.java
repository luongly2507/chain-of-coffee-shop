package com.app.coffee.controller;

import java.net.URI;
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

import com.app.coffee.payload.request.CreateCustomerRequest;
import com.app.coffee.payload.request.UpdateCustomerRequest;
import com.app.coffee.service.CustomerService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/customers")
@PreAuthorize("hasRole('ADMIN') OR hasRole('MANAGER')")
public class CustomerRestController {
    @Autowired
    private CustomerService customerService;

    @GetMapping
    public ResponseEntity<?> getAllCustomer(@RequestParam(required = false) String key,Pageable pageable) {
        return ResponseEntity.ok(customerService.searchCustomer(key, pageable));
    }
    @GetMapping("/{customerId}")
    public ResponseEntity<?> getCustomerById(@PathVariable UUID customerId) {
        return ResponseEntity.ok(customerService.getCustomerById(customerId));
    }
    @PostMapping
    public ResponseEntity<?> createCustomer(@RequestBody @Valid CreateCustomerRequest createCustomerRequest) {
        System.out.println(createCustomerRequest);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(customerService.createCustomer(createCustomerRequest).getId()).toUri();
        return ResponseEntity.created(location).build();
    }
    @PutMapping("/{customerId}")
    public ResponseEntity<?> updateCustomerById(@PathVariable UUID customerId, @RequestBody @Valid UpdateCustomerRequest updateCustomerRequest) {
        System.out.println(updateCustomerRequest);
        customerService.updateCustomer(customerId, updateCustomerRequest);
        return ResponseEntity.noContent().build();
    }
    @DeleteMapping("/{customerId}")
    public ResponseEntity<?> deleteCustomer(@PathVariable UUID customerId) {
        customerService.deleteCustomer(customerId);
        return ResponseEntity.noContent().build();
    }
}
