package com.ecommerce.monolith.order.service;

import com.ecommerce.monolith.customer.model.Customer;
import com.ecommerce.monolith.customer.service.CustomerService;
import com.ecommerce.monolith.order.dto.OrderDTO;
import com.ecommerce.monolith.order.dto.OrderRequest;
import com.ecommerce.monolith.order.model.Order;
import com.ecommerce.monolith.order.repository.OrderRepository;
import com.ecommerce.monolith.product.model.Product;
import com.ecommerce.monolith.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final CustomerService customerService;

    @Override
    public OrderDTO create(OrderRequest request) {

        if (!customerService.exists(request.getCustomerId())) {
            throw new RuntimeException("Customer not found");
        }

        Customer customer = customerService.getEntity(request.getCustomerId());

        List<Product> products =
                productRepository.findAllById(request.getProductIds());

        Order order = Order.builder()
                .createdAt(LocalDateTime.now())
                .customer(customer)
                .products(products)
                .build();

        Order saved = orderRepository.save(order);

        return mapToDTO(saved);
    }

    @Override
    public List<OrderDTO> findAll() {
        return orderRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<OrderDTO> findByCustomer(Long customerId) {

        if (!customerService.exists(customerId)) {
            throw new RuntimeException("Customer not found");
        }

        return orderRepository.findByCustomerId(customerId)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    private OrderDTO mapToDTO(Order order) {

        List<Long> productIds = order.getProducts()
                .stream()
                .map(Product::getId)
                .collect(Collectors.toList());

        return OrderDTO.builder()
                .id(order.getId())
                .createdAt(order.getCreatedAt())
                .customerId(order.getCustomer().getId())
                .productIds(productIds)
                .build();
    }
}