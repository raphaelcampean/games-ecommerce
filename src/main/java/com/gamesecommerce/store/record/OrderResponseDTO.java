package com.gamesecommerce.store.record;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.gamesecommerce.store.model.Order;

public record OrderResponseDTO(
    UUID id,
    String status,
    BigDecimal totalPrice,
    LocalDateTime createdAt,
    List<OrderItemResponseDTO> items
) {
    public OrderResponseDTO(Order order) {
        this(
            order.getId(),
            order.getStatus() != null ? order.getStatus().toString() : null, 
            order.getTotalPrice(),
            order.getCreatedAt(),
            order.getItems() == null ? List.of() : order.getItems().stream()
                .map(OrderItemResponseDTO::new) // Referência de método (mais limpo)
                .toList()
        );
    }
}