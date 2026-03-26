package com.gamesecommerce.store.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gamesecommerce.store.model.Product;
import com.gamesecommerce.store.service.ProductService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
    public ResponseEntity createProduct(@RequestBody Product product) {
        Product createdProduct = productService.create(product);

        return ResponseEntity.status(201).body(createdProduct);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity getProduct(@PathVariable UUID id) {
        Product product = productService.findById(id);

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

}
