package com.app.coffee.service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.coffee.entity.Customer;
import com.app.coffee.exception.BadRequestException;
import com.app.coffee.exception.ConflictException;
import com.app.coffee.exception.ResourceNotFoundException;
import com.app.coffee.mapper.CustomerMapper;
import com.app.coffee.payload.request.CreateCustomerRequest;
import com.app.coffee.payload.request.UpdateCustomerRequest;
import com.app.coffee.payload.response.CustomerResponse;
import com.app.coffee.repository.CustomerRepository;
import com.app.coffee.service.CustomerService;

import jakarta.validation.Valid;

@Service
@Transactional
public class CustomerServiceImpl implements CustomerService{

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerMapper customerMapper;

    // Get Mapping - Get All
    @Override
    public List<CustomerResponse> getAllCustomers() {
        return customerRepository.findAll().stream().map(
            customer -> customerMapper.toCustomerResponse(customer)
        ).toList();
    }

    // Get Mapping - Get By Page
    @Override
    public Page<CustomerResponse> getAllCustomers(Pageable pageable) {
        return customerRepository.findAll(pageable)
        .map(customer->customerMapper.toCustomerResponse(customer));
    }

    // Get Mapping - Get By Id
    @Override
    public CustomerResponse getCustomerById(UUID customerId) {
        return customerMapper.toCustomerResponse(
            customerRepository.findById(customerId)
                    .orElseThrow(() -> new ResourceNotFoundException("Customer Not Found"))); // Check customer is present
    }

    // Post Mapping - Create Customer
    @Override
    public CustomerResponse createCustomer(@Valid CreateCustomerRequest createCustomerRequest) {
        Customer customer = customerMapper.toCustomer(createCustomerRequest);
        // Check customer is not null
        if (customer == null) {
            throw new BadRequestException("Bad Request");
        }
         // Check name is unique
        if (customerRepository.existsByName(createCustomerRequest.getName())) {
            throw new ConflictException("Already Name Exists.");
        }
        return customerMapper.toCustomerResponse(customerRepository.save(customer));       
    }

    // Put Mapping - Update Customer
    @Override
    public void updateCustomer(UUID customerId, @Valid UpdateCustomerRequest updateCustomerRequest) {
        Customer customer = customerRepository.findById(customerId)
            .orElseThrow(
                ()-> new ResourceNotFoundException("Customer Not Found."));
          // Check name is unique
          if (!updateCustomerRequest.getName().equals(customer.getName())) { // compare request name and repository name
            if (customerRepository.existsByName(updateCustomerRequest.getName())) {
                throw new ConflictException("Already Name Exists.");
            }
        }
        customerMapper.updateCustomer(updateCustomerRequest, customer);
        customerRepository.save(customer);
    }

    // Delete Mapping -
    @Override
    public void deleteCustomer(UUID customerId) {
        if (customerRepository.existsById(customerId)) {
            customerRepository.deleteById(customerId);
        } else {
            throw new ResourceNotFoundException("Customer Not Found");  // Check customer is present
        }
    }

    @Override
    public Page<CustomerResponse> searchCustomer(String search, Pageable pageable) {
        return customerRepository
        .findByNameAndTelephone(search.toLowerCase(),pageable)
        .map(customer->customerMapper.toCustomerResponse(customer));
        
    }
}
