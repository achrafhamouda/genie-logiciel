package com.ecommerce.monolith.order.service;

import com.ecommerce.monolith.order.dto.OrderDTO;
import com.ecommerce.monolith.order.dto.OrderRequest;

import java.util.List;

public interface OrderService {

    OrderDTO create(OrderRequest request);

    List<OrderDTO> findAll();

    List<OrderDTO> findByCustomer(Long customerId);
}