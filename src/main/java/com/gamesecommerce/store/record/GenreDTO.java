package com.gamesecommerce.store.record;

import com.gamesecommerce.store.model.Genre;

import java.util.Optional;
import java.util.UUID;

public record GenreDTO(
        UUID id,
        String name,
        String description,
        String slug
) {
    public GenreDTO(Genre genre) {
        this(
                genre.getId(),
                genre.getName(),
                genre.getDescription(),
                genre.getSlug()
        );
    }
}