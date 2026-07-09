package com.gamesecommerce.store.repository;

import com.gamesecommerce.store.model.Platform;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PlatformRepository extends JpaRepository<Platform, UUID> {

    Optional<Platform> findBySlug(String slug);

    Optional<Platform> findByName(String name);
}