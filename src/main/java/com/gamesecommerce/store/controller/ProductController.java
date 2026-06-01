package com.gamesecommerce.store.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gamesecommerce.store.model.Product;
import com.gamesecommerce.store.service.ProductService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/products")
public class ProductController {
    @Autowired
    ProductService productService;

    @GetMapping
    public ResponseEntity getProducts() {
        return ResponseEntity.ok().body(productService.getProducts());
    }
    
    @PostMapping
    public ResponseEntity createProduct(@RequestBody @Validated Product product) {
        Product createdProduct = productService.create(product);

        return ResponseEntity.status(201).body(createdProduct);
    }
    
    @GetMapping("/{slug}")
    public ResponseEntity getProduct(@PathVariable String slug) {
        Product product = productService.findBySlug(slug);

        if (product == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok().body(product);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteProduct(@PathVariable UUID id) {
        productService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity updateProduct(@PathVariable UUID id, @RequestBody @Validated Product product) {
        Product updatedProduct = productService.update(id, product);

        return ResponseEntity.ok().body(updatedProduct);
    }

    @GetMapping("/filter")
    public ResponseEntity<List<Product>> getFilteredProducts(
        @RequestParam(required = false) String genre,
        @RequestParam(required = false) String platform,
        @RequestParam(required = false) String developer,
        @RequestParam(required = false) BigDecimal minPrice,
        @RequestParam(required = false) BigDecimal maxPrice
    ) {
        List<Product> products = productService.findWithFilters(
            genre, platform, developer, minPrice, maxPrice
        );
        
        return ResponseEntity.ok(products);
    }
}
