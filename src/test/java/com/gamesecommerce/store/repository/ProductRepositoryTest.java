package com.gamesecommerce.store.repository;

import com.gamesecommerce.store.config.AbstractPostgresContainerTest;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

import jakarta.persistence.EntityManager;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.gamesecommerce.store.model.Genre;
import com.gamesecommerce.store.model.Product;

@SpringBootTest
@ActiveProfiles("test")
public class ProductRepositoryTest extends AbstractPostgresContainerTest {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private EntityManager entityManager;

    @Test
    @DisplayName("Should find product by name")
    public void testFindByName() {
        Product product = createProduct();
        entityManager.flush();
        entityManager.clear();

        Product found = productRepository.findByName(product.getName());

        assertThat(found).isNotNull();
        assertThat(found.getName()).isEqualTo(product.getName());
    }

    @Test
    @DisplayName("Should return null when no product found by name")
    public void testFindByNameNotFound() {
        Product found = productRepository.findByName("Nonexistent Product");

        assertThat(found).isNull();
    }

    @Test
    @DisplayName("Should find product by slug")
    public void testFindBySlug() {
        Product product = createProduct();
     
        entityManager.flush();
        entityManager.clear();
     
        Product found = productRepository.findBySlug(product.getSlug());
     
        assertThat(found).isNotNull();
        assertThat(found.getSlug()).isEqualTo(product.getSlug());
    }

    @Test
    @DisplayName("Should return null when no product found by slug")
    public void testFindBySlugNotFound() {
        Product found = productRepository.findBySlug("nonexistent-slug");
        assertThat(found).isNull();
    }

    @Test
    @DisplayName("Should find products by genre slug")
    public void testFindByGenreSlug() {
        Genre genre = createGenre();
        Product product = createProduct();

        entityManager.flush();
        entityManager.clear();

        List<Product> found = productRepository.findByGenres_Slug(genre.getSlug());

        assertThat(found).isNotNull();
        assertThat(found).contains(product);
    }

    private Product createProduct() {
        Genre genre = createGenre();

        Product product = new Product();

        product.setName("Test Game");
        product.setSlug("test-game");
        product.setDescription("A test game description");
        product.setGenres(Set.of(genre));
        product.setPrice(BigDecimal.valueOf(19.99));
        return productRepository.save(product);
    }

    private Genre createGenre() {
        Genre genre = new Genre();
        genre.setName("Action");
        genre.setSlug("action");

        return genreRepository.save(genre);
    }
}
