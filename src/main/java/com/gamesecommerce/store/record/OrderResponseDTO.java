package com.gamesecommerce.store.record;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record OrderResponseDTO(
    UUID id,
    String status,
    BigDecimal totalPrice,
    LocalDateTime createdAt,
    List<OrderItemResponseDTO> items
) {}