package com.gamesecommerce.store.record;

import com.gamesecommerce.store.model.Platform;

import java.util.UUID;

public record PlatformDTO(
        UUID id,
        String name,
        String description,
        String slug
) {

    public PlatformDTO(Platform platform) {
        this(
                platform.getId(),
                platform.getName(),
                platform.getDescription(),
                platform.getSlug()
        );
    }
}