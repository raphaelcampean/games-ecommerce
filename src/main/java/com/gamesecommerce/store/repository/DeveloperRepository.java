package com.gamesecommerce.store.repository;

import com.gamesecommerce.store.model.Developer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface DeveloperRepository extends JpaRepository<Developer, UUID> {
    Optional<Developer> findBySlug(String slug);

    boolean existsByName(String name);
}

