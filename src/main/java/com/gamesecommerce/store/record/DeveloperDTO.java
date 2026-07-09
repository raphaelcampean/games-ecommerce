package com.gamesecommerce.store.record;

import com.gamesecommerce.store.model.Developer;

import java.util.UUID;

public record DeveloperDTO(
        UUID id,
        String name,
        String description,
        String slug
) {
    public DeveloperDTO(Developer developer) {
        this(
                developer.getId(),
                developer.getName(),
                developer.getDescription(),
                developer.getSlug()
        );
    }
}