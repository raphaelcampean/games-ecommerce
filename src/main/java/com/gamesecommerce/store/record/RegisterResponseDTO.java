package com.gamesecommerce.store.record;

import java.util.UUID;

public record RegisterResponseDTO(
        UUID id,
        String username,
        String email,
        String token
) {}