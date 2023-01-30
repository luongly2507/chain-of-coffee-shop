package com.app.coffee.mapper.impl;

import org.springframework.stereotype.Component;

import com.app.coffee.entity.Customer;
import com.app.coffee.mapper.CustomerMapper;
import com.app.coffee.payload.request.CreateCustomerRequest;
import com.app.coffee.payload.request.UpdateCustomerRequest;
import com.app.coffee.payload.response.CustomerResponse;

@Component
public class CustomerMapperImpl implements CustomerMapper {

    @Override
    public CustomerResponse toCustomerResponse(Customer customer) {
        if (customer == null){
            return null;
        }
        return CustomerResponse.builder()
            .id(customer.getId())
            .name(customer.getName())
            .gender(customer.getGender())
            .telephone(customer.getTelephone())
            .rank(customer.getRank())
            .accumulatedPoints(customer.getAccumulatedPoints())
            .createdAt(customer.getCreatedAt())
            .createdBy(customer.getCreatedBy())
            .lastModifiedAt(customer.getLastModifiedAt())
            .lastModifiedBy(customer.getLastModifiedBy())
            .build();
    }

    @Override
    public Customer toCustomer(CreateCustomerRequest createCustomerRequest) {
        if(createCustomerRequest == null) {
            return null;
        }
        return Customer.builder()
        .name(createCustomerRequest.getName())
        .gender(createCustomerRequest.getGender())
        .telephone(createCustomerRequest.getTelephone())
        .rank("Bạc")
        .accumulatedPoints(0)
        .build();
    }

    @Override
    public void updateCustomer(UpdateCustomerRequest updateCustomerRequest, Customer customer) {
        if(updateCustomerRequest == null){
            return;
        }
        customer.setName(updateCustomerRequest.getName());
        customer.setGender(updateCustomerRequest.getGender());
        customer.setTelephone(updateCustomerRequest.getTelephone());
        customer.setAccumulatedPoints(updateCustomerRequest.getAccumulatedPoints());
        if (updateCustomerRequest.getAccumulatedPoints() >= 500 && updateCustomerRequest.getAccumulatedPoints() < 1000){
            customer.setRank("Vàng");
        } else if (updateCustomerRequest.getAccumulatedPoints() >= 1000 && updateCustomerRequest.getAccumulatedPoints() < 1500) {
            customer.setRank("Bạch kim");
        } else if (updateCustomerRequest.getAccumulatedPoints() >= 1500){
            customer.setRank("Kim cương");
        } else{
            customer.setRank("Bạc");
        }
    }
    
}
