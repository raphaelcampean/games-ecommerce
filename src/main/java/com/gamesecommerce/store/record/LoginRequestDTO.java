package com.gamesecommerce.store.record;

public record LoginRequestDTO(
        String login,
        String password
) {}