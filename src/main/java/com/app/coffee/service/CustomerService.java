package com.app.coffee.service;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.app.coffee.payload.request.CreateCustomerRequest;
import com.app.coffee.payload.request.UpdateCustomerRequest;
import com.app.coffee.payload.response.CustomerResponse;

import jakarta.validation.Valid;

public interface CustomerService {
    public List<CustomerResponse> getAllCustomers();
    public Page<CustomerResponse> getAllCustomers(Pageable pageable);
    public CustomerResponse getCustomerById(UUID customerId);
    public void deleteCustomer(UUID customerId);
    public CustomerResponse createCustomer(@Valid CreateCustomerRequest createCustomerRequest);
    public void updateCustomer(UUID customerId, @Valid UpdateCustomerRequest updateCustomerRequest);
    public Page<CustomerResponse> searchCustomer(String search, Pageable pageable);
}
