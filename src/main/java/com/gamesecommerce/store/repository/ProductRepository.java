package com.gamesecommerce.store.repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.gamesecommerce.store.model.Product;
@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {
    Product findByName(String name);
    Product findBySlug(String slug);
    List<Product> findByGenres_Slug(String genreSlug);
    List<Product> findByDeveloper_Slug(String developerSlug);
    List<Product> findByPlatforms_Slug(String platformSlug);

    @Query("SELECT DISTINCT p FROM Product p " +
       "LEFT JOIN FETCH p.genres " +
       "LEFT JOIN FETCH p.platforms " +
       "LEFT JOIN FETCH p.developer")
    List<Product> findWithFilters(
        @Param("genreSlug") String genreSlug,
        @Param("platformSlug") String platformSlug,
        @Param("developerSlug") String developerSlug,
        @Param("minPrice") BigDecimal minPrice,
        @Param("maxPrice") BigDecimal maxPrice
    );
}
