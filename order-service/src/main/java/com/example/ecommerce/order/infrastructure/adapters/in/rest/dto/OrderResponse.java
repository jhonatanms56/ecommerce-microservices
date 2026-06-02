package com.example.ecommerce.order.infrastructure.adapters.in.rest.dto;

import com.example.ecommerce.order.domain.model.Order;
import com.example.ecommerce.order.domain.model.OrderItem;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class OrderResponse {

    private UUID id;
    private String customerId;
    private String status;
    private BigDecimal totalAmount;
    private LocalDateTime createdAt;
    private List<OrderItemResponse> items;

    public static class OrderItemResponse {
        private String productId;
        private String productName;
        private int quantity;
        private BigDecimal unitPrice;
        private BigDecimal totalPrice;

        public String getProductId() { return productId; }
        public void setProductId(String productId) { this.productId = productId; }

        public String getProductName() { return productName; }
        public void setProductName(String productName) { this.productName = productName; }

        public int getQuantity() { return quantity; }
        public void setQuantity(int quantity) { this.quantity = quantity; }

        public BigDecimal getUnitPrice() { return unitPrice; }
        public void setUnitPrice(BigDecimal unitPrice) { this.unitPrice = unitPrice; }

        public BigDecimal getTotalPrice() { return totalPrice; }
        public void setTotalPrice(BigDecimal totalPrice) { this.totalPrice = totalPrice; }
    }

    // Factory method — maps domain Order to this response
    public static OrderResponse from(Order order) {
        OrderResponse response = new OrderResponse();
        response.id = order.getId();
        response.customerId = order.getCustomerId();
        response.status = order.getStatus().name();
        response.totalAmount = order.totalAmount();
        response.createdAt = order.getCreatedAt();
        response.items = order.getItems().stream()
                .map(OrderResponse::fromItem)
                .toList();
        return response;
    }

    private static OrderItemResponse fromItem(OrderItem item) {
        OrderItemResponse response = new OrderItemResponse();
        response.productId = item.getProductId();
        response.productName = item.getProductName();
        response.quantity = item.getQuantity();
        response.unitPrice = item.getUnitPrice();
        response.totalPrice = item.totalPrice();
        return response;
    }

    public UUID getId() { return id; }
    public String getCustomerId() { return customerId; }
    public String getStatus() { return status; }
    public BigDecimal getTotalAmount() { return totalAmount; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public List<OrderItemResponse> getItems() { return items; }
}

