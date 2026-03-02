package com.ecommerce.monolith.customer.service;

import com.ecommerce.monolith.customer.dto.CustomerDTO;
import com.ecommerce.monolith.customer.dto.CreateCustomerRequest;
import com.ecommerce.monolith.customer.model.Customer;

import java.util.List;

public interface CustomerService {

    CustomerDTO create(CreateCustomerRequest request);

    CustomerDTO getById(Long id);

    List<CustomerDTO> getAll();

    boolean exists(Long id);

    Customer getEntity(Long id);
}