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
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @GetMapping
    @PreAuthorize("hasAuthority('PRIVILEGE_READ_CUSTOMER')")
    public ResponseEntity<?> getAllCustomers() {
        return ResponseEntity.ok(customerService.getAllCustomers());
    }

    @GetMapping("/page")
    @PreAuthorize("hasAuthority('PRIVILEGE_READ_CUSTOMER')")
    public ResponseEntity<?> getAllCustomers(Pageable pageable) {
        return ResponseEntity.ok(customerService.getAllCustomers(pageable));
    }
    @GetMapping("/search")
    @PreAuthorize("hasAuthority('PRIVILEGE_READ_CUSTOMER')")
    public ResponseEntity<?> getAllCustomersByName(@RequestParam String key, Pageable pageable) {
        return ResponseEntity.ok(customerService.searchCustomer(key,pageable));
    }
    @GetMapping("/{customerId}")
    @PreAuthorize("hasAuthority('PRIVILEGE_READ_CUSTOMER')")
    public ResponseEntity<?> getCustomerById(
            @PathVariable UUID customerId) {
        return ResponseEntity.ok(customerService.getCustomerById(customerId));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('PRIVILEGE_CREATE_CUSTOMER')")
    public ResponseEntity<?> createCustomer(@RequestBody @Valid CreateCustomerRequest createCustomerRequest) {
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(customerService.createCustomer(createCustomerRequest).getId()).toUri();
        System.out.println(location);
        return ResponseEntity.created(location).build();
    }

    @PutMapping("{customerId}")
    @PreAuthorize("hasAuthority('PRIVILEGE_UPDATE_CUSTOMER')")
    public ResponseEntity<?> updateCustomer(
            @PathVariable UUID customerId,
            @RequestBody @Valid UpdateCustomerRequest updateCustomerRequest) {
        customerService.updateCustomer(customerId, updateCustomerRequest);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("{customerId}")
    @PreAuthorize("hasAuthority('PRIVILEGE_DELETE_CUSTOMER')")
    public ResponseEntity<?> deleteCustomer(
            @PathVariable UUID customerId) {
        customerService.deleteCustomer(customerId);
        return ResponseEntity.noContent().build();
    }

}
