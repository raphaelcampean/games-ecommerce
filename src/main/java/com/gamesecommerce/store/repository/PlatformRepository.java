package com.gamesecommerce.store.repository;

import com.gamesecommerce.store.model.Platform;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface PlatformRepository extends JpaRepository<Platform, UUID> {
    Optional<Platform> findBySlug(String slug);

    boolean existsByName(String name);
}