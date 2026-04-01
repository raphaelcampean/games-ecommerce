package com.gamesecommerce.store.record;

import java.util.UUID;

public record PaymentDTO(
    Long amount,
    UUID orderId
) {}
