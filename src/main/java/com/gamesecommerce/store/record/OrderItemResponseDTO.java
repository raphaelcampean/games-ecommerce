package com.gamesecommerce.store.record;

import java.math.BigDecimal;
import java.util.UUID;

public record OrderItemResponseDTO(
    UUID productId,
    String productName,
    BigDecimal unitPrice,
    Integer quantity
) {}