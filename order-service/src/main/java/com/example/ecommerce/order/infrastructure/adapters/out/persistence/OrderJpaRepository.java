package com.example.ecommerce.order.infrastructure.adapters.out.persistence;

import com.example.ecommerce.order.infrastructure.adapters.out.persistence.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface OrderJpaRepository extends JpaRepository<OrderEntity, UUID> {

    List<OrderEntity> findByCustomerId(String customerId);
}
