package com.gamesecommerce.store.controller;

import com.gamesecommerce.store.model.Order;
import com.gamesecommerce.store.model.User;
import com.gamesecommerce.store.record.OrderRequestDTO;
import com.gamesecommerce.store.record.OrderResponseDTO;
import com.gamesecommerce.store.repository.OrderRepository;
import com.gamesecommerce.store.repository.UserRepository;
import com.gamesecommerce.store.service.OrderService;

import jakarta.validation.Valid;

import java.util.List;
import java.util.UUID;

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

    @GetMapping
    public ResponseEntity<List<OrderResponseDTO>> getOrders(@AuthenticationPrincipal UserDetails userDetails) {
        User user = getUserFromUserDetails(userDetails);
        if (user == null) return ResponseEntity.status(401).build();

        // Transforme sua lista de Order em OrderResponseDTO aqui
        List<OrderResponseDTO> response = orderService.getOrderByUser(user)
            .stream()
            .map(order -> new OrderResponseDTO(order)) // Exemplo de conversão
            .toList();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable @Valid UUID id, @AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            return ResponseEntity.status(401).body(null);
        }

        User user = userRepository.findByUsername(userDetails.getUsername());

        if (user == null) {
            return ResponseEntity.status(404).body(null);
        }

        Order order = orderService.getOrderByUser(user).stream()
            .filter(o -> o.getId().equals(id))
            .findFirst()
            .orElse(null);

        if (order == null) {
            return ResponseEntity.status(404).body(null);
        }

        return ResponseEntity.ok().body(order);
    }

    @PostMapping
    public ResponseEntity<?> createOrder( @RequestBody @Valid OrderRequestDTO orderRequestDTO, @AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            return ResponseEntity.status(401).body("User not authenticated");
        }

        User user = getUserFromUserDetails(userDetails);

        if (user == null) return ResponseEntity.status(401).build();

        OrderResponseDTO response = orderService.createOrder(orderRequestDTO, user);

        return ResponseEntity.created(null).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> addProductToOrder(@PathVariable @Valid UUID id, @RequestBody @Valid OrderRequestDTO orderRequestDTO, @AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            return ResponseEntity.status(401).body("User not authenticated");
        }

        User user = getUserFromUserDetails(userDetails);

        if (user == null) return ResponseEntity.status(401).build();

        OrderResponseDTO response = orderService.addProductToOrder(id, orderRequestDTO, user);

        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> removeProductFromOrder(@PathVariable @Valid UUID id, @RequestBody @Valid OrderRequestDTO orderRequestDTO, @AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            return ResponseEntity.status(401).body("User not authenticated");
        }

        User user = getUserFromUserDetails(userDetails);

        if (user == null) return ResponseEntity.status(401).build();

        OrderResponseDTO response = orderService.removeProductFromOrder(id, orderRequestDTO, user);

        return ResponseEntity.ok().body(response);
    }

    private User getUserFromUserDetails(UserDetails userDetails) {
        User user = userRepository.findByUsername(userDetails.getUsername());

        if (user == null) {
            return null;
        }

        return user;
    }
}