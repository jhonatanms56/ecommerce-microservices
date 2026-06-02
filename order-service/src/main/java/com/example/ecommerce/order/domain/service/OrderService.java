package com.example.ecommerce.order.domain.service;

import com.example.ecommerce.order.domain.model.Order;
import com.example.ecommerce.order.domain.model.OrderItem;
import com.example.ecommerce.order.domain.port.in.CreateOrderUseCase;
import com.example.ecommerce.order.domain.port.in.GetOrderUseCase;
import com.example.ecommerce.order.domain.port.out.OrderRepository;

import java.util.List;
import java.util.UUID;

public class OrderService implements CreateOrderUseCase, GetOrderUseCase {
    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public Order createOrder(String customerId, List<OrderItem> items) {
        Order order = Order.create(customerId);
        items.forEach(order::addItem);
        return orderRepository.save(order);
    }

    @Override
    public Order getById(UUID id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found: " + id));
    }

    @Override
    public List<Order> getByCustomerId(String customerId) {
        return orderRepository.findByCustomerId(customerId);
    }
}
