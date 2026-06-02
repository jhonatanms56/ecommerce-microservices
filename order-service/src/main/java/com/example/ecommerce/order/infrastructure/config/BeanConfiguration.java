package com.example.ecommerce.order.infrastructure.config;

import com.example.ecommerce.order.domain.port.in.CreateOrderUseCase;
import com.example.ecommerce.order.domain.port.in.GetOrderUseCase;
import com.example.ecommerce.order.domain.port.out.OrderRepository;
import com.example.ecommerce.order.domain.service.OrderService;
import com.example.ecommerce.order.infrastructure.adapters.out.persistence.OrderJpaRepository;
import com.example.ecommerce.order.infrastructure.adapters.out.persistence.OrderPersistenceAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

    @Bean
    public OrderRepository orderRepository(OrderJpaRepository jpaRepository) {
        return new OrderPersistenceAdapter(jpaRepository);
    }

    @Bean
    public CreateOrderUseCase createOrderUseCase(OrderRepository orderRepository) {
        return new OrderService(orderRepository);
    }

    @Bean
    public GetOrderUseCase getOrderUseCase(OrderRepository orderRepository) {
        return new OrderService(orderRepository);
    }
}
