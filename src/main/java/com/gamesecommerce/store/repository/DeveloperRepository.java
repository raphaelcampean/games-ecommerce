package com.gamesecommerce.store.repository;

import com.gamesecommerce.store.model.Developer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface DeveloperRepository extends JpaRepository<Developer, UUID> {
    Optional<Developer> findBySlug(String slug);

    Optional<Developer> findByName(String name);
}