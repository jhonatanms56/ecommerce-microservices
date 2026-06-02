package com.example.ecommerce.order.infrastructure.adapters.in.rest.dto;

import java.math.BigDecimal;
import java.util.List;

public class CreateOrderRequest {

    private String customerId;
    private List<OrderItemRequest> items;

    public static class OrderItemRequest {
        private String productId;
        private String productName;
        private int quantity;
        private BigDecimal unitPrice;

        public String getProductId() { return productId; }
        public void setProductId(String productId) { this.productId = productId; }

        public String getProductName() { return productName; }
        public void setProductName(String productName) { this.productName = productName; }

        public int getQuantity() { return quantity; }
        public void setQuantity(int quantity) { this.quantity = quantity; }

        public BigDecimal getUnitPrice() { return unitPrice; }
        public void setUnitPrice(BigDecimal unitPrice) { this.unitPrice = unitPrice; }
    }

    public String getCustomerId() { return customerId; }
    public void setCustomerId(String customerId) { this.customerId = customerId; }

    public List<OrderItemRequest> getItems() { return items; }
    public void setItems(List<OrderItemRequest> items) { this.items = items; }
}
