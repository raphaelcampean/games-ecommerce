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
            order.getTotalPrice(),
            order.getStatus().toString(), // Converte o Enum para String
            order.getCreatedAt()
        );
    }
}