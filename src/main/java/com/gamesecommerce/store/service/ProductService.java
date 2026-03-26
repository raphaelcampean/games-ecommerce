package com.gamesecommerce.store.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gamesecommerce.store.model.Product;
import com.gamesecommerce.store.repository.ProductRepository;

@Service
public class ProductService {
    @Autowired
    ProductRepository productRepository;

    public Product findByName(String name) {
        return productRepository.findByName(name);
    }

    public Product create(Product product) {
        Product existingProduct = findByName(product.getName());
        
        if (existingProduct != null) {
            throw new RuntimeException("Product with name '" + product.getName() + "' already exists.");
        }

        return productRepository.save(product);
    }

    public void deleteById(UUID id) {
        productRepository.deleteById(id);
    }

    public Product update(Product product) {
        return productRepository.save(product);
    }

    public Iterable<Product> getProducts() {
        return productRepository.findAll();
    }

    public Product findById(UUID id) {
        return productRepository.findById(id).orElse(null);
    }
}
