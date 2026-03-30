package com.ecommerce.monolith.customer.mapper;

import com.ecommerce.monolith.customer.dto.CustomerDTO;
import com.ecommerce.monolith.customer.dto.CreateCustomerRequest;
import com.ecommerce.monolith.customer.model.Customer;

public interface CustomerMapper {

    CustomerDTO toDTO(Customer customer);

    Customer toEntity(CreateCustomerRequest request);
}