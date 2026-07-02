package com.gamesecommerce.store.controller;

import java.util.UUID;

import com.gamesecommerce.store.record.OrderResponseDTO;
import com.gamesecommerce.store.record.ProductDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
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
@RequestMapping("/produtos")
public class ProductController {
    @Autowired
    ProductService productService;

    @GetMapping
    public ResponseEntity<Page<ProductDTO>> listAll(
            @PageableDefault(size = 20, sort = "name") Pageable pageable) {

        Page<ProductDTO> products = productService.getProducts(pageable);

        return ResponseEntity.ok(products);
    }

    @GetMapping("/{slug}")
    public ResponseEntity<ProductDTO> getProduct(
            @PathVariable String slug
    ) {
        Product product = productService.findBySlug(slug);

        if (product == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(new ProductDTO(product));
    }
}
