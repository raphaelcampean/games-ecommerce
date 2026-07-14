package com.gamesecommerce.store.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.gamesecommerce.store.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {

    @Query(value = "SELECT DISTINCT p FROM Product p " +
            "LEFT JOIN FETCH p.genres " +
            "LEFT JOIN FETCH p.platforms " +
            "LEFT JOIN FETCH p.developer",
            countQuery = "SELECT count(DISTINCT p) FROM Product p")
    Page<Product> findAll(Pageable pageable);

    Product findByName(String name);
    @Query("""
        SELECT DISTINCT p FROM Product p
        LEFT JOIN FETCH p.genres
        LEFT JOIN FETCH p.platforms
        LEFT JOIN FETCH p.developer
        WHERE p.slug = :slug
    """)
    Product findBySlug(@Param("slug") String slug);

    @Query("""
        SELECT DISTINCT p FROM Product p
        LEFT JOIN FETCH p.genres
        LEFT JOIN FETCH p.platforms
        LEFT JOIN FETCH p.developer
        WHERE p.id = :id
    """)
    Optional<Product> findProductById(@Param("id") UUID id);
    List<Product> findByGenres_Slug(String genreSlug);
    List<Product> findByDeveloper_Slug(String developerSlug);
    List<Product> findByPlatforms_Slug(String platformSlug);
    List<Product> findByStockQuantityLessThan(int quantity);
    List<Product> findTop5ByOrderByCreatedAtDesc();
    long countByStockQuantityLessThan(int i);

    Optional<Product> findBySlugOrName(String slug, String name);
}