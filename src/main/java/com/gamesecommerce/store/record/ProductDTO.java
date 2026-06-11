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
        Set<String> genres,
        Set<String> platforms,
        String developerName
) {
    public ProductDTO(Product product) {
        this(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getStockQuantity(),
                product.getSlug(),
                product.getGenres().stream().map(g -> g.getName()).collect(Collectors.toSet()),
                product.getPlatforms().stream().map(p -> p.getName()).collect(Collectors.toSet()),
                product.getDeveloper() != null ? product.getDeveloper().getName() : null
        );
    }
}
