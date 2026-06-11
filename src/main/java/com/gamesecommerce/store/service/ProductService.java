package com.gamesecommerce.store.service;

import java.util.List;
import java.util.UUID;

import com.gamesecommerce.store.record.ProductDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    public Page<ProductDTO> getProducts(Pageable pageable) {
        return productRepository.findAll(pageable)
                .map(ProductDTO::new);
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

    public long countProducts(){
        return productRepository.count();
    }

    public List<Product> findTop5ByOrderByCreatedAtDesc() {
        return productRepository.findTop5ByOrderByCreatedAtDesc();
    }

    public List<Product> findLowStockProducts(int threshold) {
        return productRepository.findByStockQuantityLessThan(threshold);
    }

     public long countLowStock() {
        return productRepository.countByStockQuantityLessThan(5);
    }
}
