package com.gamesecommerce.store.record;

import java.math.BigDecimal;
import java.util.UUID;

import com.gamesecommerce.store.model.OrderItem;

public record OrderItemResponseDTO(
    UUID productId,
    String productName,
    Integer quantity,
    BigDecimal price
) {
    public OrderItemResponseDTO(OrderItem item) {
        this(
            item.getProduct().getId(),
            item.getProduct().getName(),
            item.getQuantity(),
            item.getPrice()
        );
    }
}