package com.example.ecommerce.order.domain.port.in;

import com.example.ecommerce.order.domain.model.Order;
import com.example.ecommerce.order.domain.model.OrderItem;

import java.util.List;

public interface CreateOrderUseCase {
    Order createOrder(String customerId, List<OrderItem> items);
}
