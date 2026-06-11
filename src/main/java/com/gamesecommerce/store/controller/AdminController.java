package com.gamesecommerce.store.controller;

import com.gamesecommerce.store.record.ProductDTO;
import com.gamesecommerce.store.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    ProductService productService;

    @GetMapping("/admin")
    public ResponseEntity<Page<ProductDTO>> getIndex(
            @PageableDefault(size = 10, sort = "name") Pageable pageable) {

        return ResponseEntity.ok(productService.getProducts(pageable));
    }

    @GetMapping("/admin/dashboard")
    public ResponseEntity getDashboardData() {
        Map<String, Object> stats = new HashMap<>();
        stats.put("totalProducts", productService.countProducts());
        stats.put("lowStockCount", productService.countLowStock());
        stats.put("recentProducts", productService.findTop5ByOrderByCreatedAtDesc());

        return ResponseEntity.ok().body(stats);
    }

}
