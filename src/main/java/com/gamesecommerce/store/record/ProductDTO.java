package com.gamesecommerce.store.record;

import com.gamesecommerce.store.model.Product;

import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public record ProductDTO(
        UUID id,
        String name,
        String description,
        BigDecimal price,
        int stockQuantity,
        String slug,
        Set<GenreDTO> genres,
        Set<PlatformDTO> platforms,
        String developerName,
        String imageUrl
) {
    public ProductDTO(Product product) {
        this(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getStockQuantity(),
                product.getSlug(),
                product.getGenres()
                        .stream()
                        .map(GenreDTO::new)
                        .collect(Collectors.toSet()),
                product.getPlatforms()
                        .stream()
                        .map(PlatformDTO::new)
                        .collect(Collectors.toSet()),
                product.getDeveloper() != null
                        ? product.getDeveloper().getName()
                        : null,
                product.getImageUrl()
        );
    }
}
