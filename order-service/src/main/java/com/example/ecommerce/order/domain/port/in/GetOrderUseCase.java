package com.example.ecommerce.order.domain.port.in;

import com.example.ecommerce.order.domain.model.Order;

import java.util.List;
import java.util.UUID;

public interface GetOrderUseCase {
    Order getById(UUID id);
    List<Order> getByCustomerId(String customerId);
}
