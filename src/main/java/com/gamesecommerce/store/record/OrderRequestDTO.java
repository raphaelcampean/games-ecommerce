package com.gamesecommerce.store.record;

import java.util.UUID;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record OrderRequestDTO(
    @NotNull UUID productId,
    @NotNull @Min(1) Integer quantity
) {}