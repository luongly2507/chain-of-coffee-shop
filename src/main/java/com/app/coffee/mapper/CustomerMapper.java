package com.app.coffee.mapper;

import com.app.coffee.entity.Customer;
import com.app.coffee.payload.request.CreateCustomerRequest;
import com.app.coffee.payload.request.UpdateCustomerRequest;
import com.app.coffee.payload.response.CustomerResponse;

public interface CustomerMapper {

    public CustomerResponse toCustomerResponse(Customer customer);
    public Customer toCustomer(CreateCustomerRequest createCustomerRequest);
    public void updateCustomer (UpdateCustomerRequest updateCustomer, Customer customer);
}
