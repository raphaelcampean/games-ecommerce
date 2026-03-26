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

        product.setSlug(generateSlug(product.getName()));

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

    public Product update(UUID id, Product product) {
        Product existingProduct = findById(id);

        if (existingProduct == null) {
            throw new RuntimeException("Product with id '" + id + "' not found.");
        }

        if (product.getName() != null && !product.getName().equals(existingProduct.getName())) {
            existingProduct.setName(product.getName());
            existingProduct.setSlug(generateSlug(product.getName()));
        } else {
            existingProduct.setName(existingProduct.getName());
            existingProduct.setSlug(existingProduct.getSlug());
        }

        existingProduct.setDescription(product.getDescription() == null ? existingProduct.getDescription() : product.getDescription());
        existingProduct.setPrice(product.getPrice() == null ? existingProduct.getPrice() : product.getPrice());
        existingProduct.setStockQuantity(product.getStockQuantity() != existingProduct.getStockQuantity() ? product.getStockQuantity() : existingProduct.getStockQuantity());
        return productRepository.save(existingProduct);
    }

    public String generateSlug(String name) {
        return name.toLowerCase().replaceAll("[^a-z0-9]+", "-").replaceAll("^-|-$", "");
    }

    public Product findBySlug(String slug) {
        return productRepository.findBySlug(slug);
    }
}
