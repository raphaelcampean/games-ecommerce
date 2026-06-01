package com.gamesecommerce.store.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gamesecommerce.store.model.Product;
@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {
    Product findByName(String name);
    Product findBySlug(String slug);
    List<Product> findByGenres_Slug(String genreSlug);
    List<Product> findByDeveloper_Slug(String developerSlug);
    List<Product> findByPlatforms_Slug(String platformSlug);
}
