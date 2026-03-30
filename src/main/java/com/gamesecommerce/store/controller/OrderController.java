package com.gamesecommerce.store.controller;

import com.gamesecommerce.store.model.Order;
import com.gamesecommerce.store.model.User;
import com.gamesecommerce.store.record.OrderRequestDTO;
import com.gamesecommerce.store.record.OrderResponseDTO;
import com.gamesecommerce.store.repository.UserRepository;
import com.gamesecommerce.store.service.OrderService;

import jakarta.validation.Valid;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping
    public ResponseEntity<?> createOrder( @RequestBody @Valid OrderRequestDTO orderRequestDTO, @AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            return ResponseEntity.status(401).body("User not authenticated");
        }

        User user = userRepository.findByUsername(userDetails.getUsername());

        if (user == null) {
            return ResponseEntity.status(404).body("User not found");
        }

        OrderResponseDTO response = orderService.createOrder(orderRequestDTO, user);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> addProductToOrder(@PathVariable @Valid UUID id, @RequestBody @Valid OrderRequestDTO orderRequestDTO, @AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            return ResponseEntity.status(401).body("User not authenticated");
        }

        User user = userRepository.findByUsername(userDetails.getUsername());

        if (user == null) {
            return ResponseEntity.status(404).body("User not found");
        }

        OrderResponseDTO response = orderService.addProductToOrder(id, orderRequestDTO, user);

        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> removeProductFromOrder(@PathVariable @Valid UUID id, @RequestBody @Valid OrderRequestDTO orderRequestDTO, @AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            return ResponseEntity.status(401).body("User not authenticated");
        }

        User user = userRepository.findByUsername(userDetails.getUsername());

        if (user == null) {
            return ResponseEntity.status(404).body("User not found");
        }

        OrderResponseDTO response = orderService.removeProductFromOrder(id, orderRequestDTO, user);

        return ResponseEntity.ok().body(response);
    }
}