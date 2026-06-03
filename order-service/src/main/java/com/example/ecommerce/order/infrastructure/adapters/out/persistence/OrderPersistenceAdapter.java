package com.example.ecommerce.order.infrastructure.adapters.out.persistence;

import com.example.ecommerce.order.domain.model.Order;
import com.example.ecommerce.order.domain.model.OrderItem;
import com.example.ecommerce.order.domain.port.out.OrderRepository;
import com.example.ecommerce.order.infrastructure.adapters.out.persistence.entity.OrderEntity;
import com.example.ecommerce.order.infrastructure.adapters.out.persistence.entity.OrderItemEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class OrderPersistenceAdapter implements OrderRepository {

    private final OrderJpaRepository jpaRepository;

    public OrderPersistenceAdapter(OrderJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Order save(Order order) {
        OrderEntity entity = toEntity(order);
        OrderEntity saved = jpaRepository.save(entity);
        return toDomain(saved);
    }

    @Override
    public Optional<Order> findById(UUID id) {
        return jpaRepository.findById(id).map(this::toDomain);
    }

    @Override
    public List<Order> findByCustomerId(String customerId) {
        return jpaRepository.findByCustomerId(customerId)
                .stream()
                .map(this::toDomain)
                .toList();
    }

    // Domain → Entity (for writing to DB)
    private OrderEntity toEntity(Order order) {
        OrderEntity entity = new OrderEntity();
        entity.setId(order.getId());
        entity.setCustomerId(order.getCustomerId());
        entity.setStatus(order.getStatus());
        entity.setCreatedAt(order.getCreatedAt());

        List<OrderItemEntity> itemEntities = order.getItems().stream()
                .map(item -> toItemEntity(item, entity))
                .toList();
        entity.setItems(itemEntities);

        return entity;
    }

    private OrderItemEntity toItemEntity(OrderItem item, OrderEntity orderEntity) {
        OrderItemEntity entity = new OrderItemEntity();
        entity.setOrder(orderEntity);
        entity.setProductId(item.getProductId());
        entity.setProductName(item.getProductName());
        entity.setQuantity(item.getQuantity());
        entity.setUnitPrice(item.getUnitPrice());
        return entity;
    }

    // Entity → Domain (for reading from DB)
    private Order toDomain(OrderEntity entity) {
        List<OrderItem> items = entity.getItems().stream()
                .map(this::toItemDomain)
                .toList();

        return Order.reconstitute(
                entity.getId(),
                entity.getCustomerId(),
                items,
                entity.getStatus(),
                entity.getCreatedAt()
        );
    }

    private OrderItem toItemDomain(OrderItemEntity entity) {
        return new OrderItem(
                entity.getProductId(),
                entity.getProductName(),
                entity.getQuantity(),
                entity.getUnitPrice()
        );
    }
}
