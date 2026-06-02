package com.example.ecommerce.order.infrastructure.adapters.in.rest;

import com.example.ecommerce.order.domain.model.Order;
import com.example.ecommerce.order.domain.model.OrderItem;
import com.example.ecommerce.order.domain.port.in.CreateOrderUseCase;
import com.example.ecommerce.order.domain.port.in.GetOrderUseCase;
import com.example.ecommerce.order.infrastructure.adapters.in.rest.dto.CreateOrderRequest;
import com.example.ecommerce.order.infrastructure.adapters.in.rest.dto.OrderResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/orders")
@Tag(name = "Orders", description = "Order management endpoints")
public class OrderController {

    private final CreateOrderUseCase createOrderUseCase;
    private final GetOrderUseCase getOrderUseCase;

    public OrderController(CreateOrderUseCase createOrderUseCase, GetOrderUseCase getOrderUseCase) {
        this.createOrderUseCase = createOrderUseCase;
        this.getOrderUseCase = getOrderUseCase;
    }

    @PostMapping
    @Operation(summary = "Create a new order")
    public ResponseEntity<OrderResponse> createOrder(@RequestBody CreateOrderRequest request) {
        List<OrderItem> items = request.getItems().stream()
                .map(i -> new OrderItem(
                        i.getProductId(),
                        i.getProductName(),
                        i.getQuantity(),
                        i.getUnitPrice()
                ))
                .toList();

        Order order = createOrderUseCase.createOrder(request.getCustomerId(), items);
        return ResponseEntity.status(HttpStatus.CREATED).body(OrderResponse.from(order));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get order by ID")
    public ResponseEntity<OrderResponse> getById(@PathVariable UUID id) {
        Order order = getOrderUseCase.getById(id);
        return ResponseEntity.ok(OrderResponse.from(order));
    }

    @GetMapping("/customer/{customerId}")
    @Operation(summary = "Get all orders for a customer")
    public ResponseEntity<List<OrderResponse>> getByCustomerId(@PathVariable String customerId) {
        List<OrderResponse> orders = getOrderUseCase.getByCustomerId(customerId)
                .stream()
                .map(OrderResponse::from)
                .toList();
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/health")
    @Operation(summary = "Health check")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Order Service is up and running!");
    }
}

