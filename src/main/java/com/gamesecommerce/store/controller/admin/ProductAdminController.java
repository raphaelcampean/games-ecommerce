package com.gamesecommerce.store.controller.admin;

import com.gamesecommerce.store.model.Product;
import com.gamesecommerce.store.record.ProductDTO;
import com.gamesecommerce.store.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/admin/produtos")
public class ProductAdminController {
    @Autowired
    ProductService productService;

    @GetMapping
    public ResponseEntity<Page<ProductDTO>> listAll(
            @PageableDefault(size = 20, sort = "name") Pageable pageable) {

        Page<ProductDTO> products = productService.getProducts(pageable);

        return ResponseEntity.ok(products);
    }

    @GetMapping("/{id}")
    public ResponseEntity getProduct(@PathVariable UUID id){
        Optional<Product> product = productService.findProductById(id);

        return ResponseEntity.ok(product);
    }

    @PostMapping
    public ResponseEntity createProduct(@RequestBody @Validated Product product) {
        Product createdProduct = productService.create(product);

        return ResponseEntity.status(201).body(createdProduct);
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
}
