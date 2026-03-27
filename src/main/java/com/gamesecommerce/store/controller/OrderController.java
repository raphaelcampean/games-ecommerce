package com.gamesecommerce.store.controller;

import com.gamesecommerce.store.model.User;
import com.gamesecommerce.store.record.OrderRequestDTO;
import com.gamesecommerce.store.record.OrderResponseDTO;
import com.gamesecommerce.store.repository.UserRepository;
import com.gamesecommerce.store.service.OrderService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

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

        var newOrder = orderService.createOrder(orderRequestDTO, user);

        OrderResponseDTO response = new OrderResponseDTO(
            newOrder.getId(),
            newOrder.getStatus().name(),
            newOrder.getTotalPrice().doubleValue(),
            newOrder.getCreatedAt(),
            newOrder.getItems().stream()
                .map(item -> new OrderItemResponseDTO(
                    item.getId(),
                    item.getProduct().getName(),
                    item.getPrice().doubleValue(),
                    item.getQuantity()
                ))
                .toList()
        );

        return ResponseEntity.ok(response);
    }
}