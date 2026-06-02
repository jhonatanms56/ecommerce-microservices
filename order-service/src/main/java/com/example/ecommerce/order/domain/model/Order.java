package com.example.ecommerce.order.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class Order {

    private UUID id;
    private String customerId;
    private List<OrderItem> items;
    private OrderStatus status;
    private LocalDateTime createdAt;

    // Private — use the factory method below
    private Order(String customerId) {
        this.id = UUID.randomUUID();
        this.customerId = customerId;
        this.items = new ArrayList<>();
        this.status = OrderStatus.PENDING;
        this.createdAt = LocalDateTime.now();
    }

    // Factory method — the only way to create a new order
    public static Order create(String customerId) {
        if (customerId == null || customerId.isBlank()) {
            throw new IllegalArgumentException("Customer ID cannot be empty");
        }
        return new Order(customerId);
    }

    // Reconstruct from persistence (no new UUID, no new timestamp)
    public static Order reconstitute(UUID id, String customerId, List<OrderItem> items,
                                     OrderStatus status, LocalDateTime createdAt) {
        Order order = new Order(customerId);
        order.id = id;
        order.items = new ArrayList<>(items);
        order.status = status;
        order.createdAt = createdAt;
        return order;
    }

    public void addItem(OrderItem item) {
        if (status != OrderStatus.PENDING) {
            throw new IllegalStateException("Cannot add items to an order that is not PENDING");
        }
        items.add(item);
    }

    public void confirm() {
        if (status != OrderStatus.PENDING) {
            throw new IllegalStateException("Only PENDING orders can be confirmed");
        }
        if (items.isEmpty()) {
            throw new IllegalStateException("Cannot confirm an order with no items");
        }
        this.status = OrderStatus.CONFIRMED;
    }

    public void cancel() {
        if (status == OrderStatus.SHIPPED || status == OrderStatus.DELIVERED) {
            throw new IllegalStateException("Cannot cancel an order that has already been shipped or delivered");
        }
        this.status = OrderStatus.CANCELLED;
    }

    public BigDecimal totalAmount() {
        return items.stream()
                .map(OrderItem::totalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public UUID getId() { return id; }
    public String getCustomerId() { return customerId; }
    public List<OrderItem> getItems() { return Collections.unmodifiableList(items); }
    public OrderStatus getStatus() { return status; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}
