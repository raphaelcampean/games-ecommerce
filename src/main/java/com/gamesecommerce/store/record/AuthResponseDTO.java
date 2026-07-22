package com.gamesecommerce.store.record;

public record AuthResponseDTO(
        UserDTO user,
        String token
) {}