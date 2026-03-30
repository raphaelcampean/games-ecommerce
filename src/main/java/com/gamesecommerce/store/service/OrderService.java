package com.gamesecommerce.store.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gamesecommerce.store.exception.BusinessException;
import com.gamesecommerce.store.model.Order;
import com.gamesecommerce.store.model.OrderItem;
import com.gamesecommerce.store.model.User;
import com.gamesecommerce.store.record.OrderItemResponseDTO;
import com.gamesecommerce.store.record.OrderRequestDTO;
import com.gamesecommerce.store.record.OrderResponseDTO;
import com.gamesecommerce.store.repository.OrderRepository;

import jakarta.transaction.Transactional;

@Service
public class OrderService {
    @Autowired
    OrderRepository orderRepository;

    @Autowired
    ProductService productService;

    @Transactional
    public OrderResponseDTO createOrder(OrderRequestDTO dto, User user) {
        var product = productService.findById(dto.productId());
        
        if (product.getStockQuantity() < dto.quantity()) {
            throw new BusinessException("Estoque insuficiente para: " + product.getName());
        }

        product.setStockQuantity(product.getStockQuantity() - dto.quantity());
        productService.update(product);

        BigDecimal totalPrice = product.getPrice().multiply(BigDecimal.valueOf(dto.quantity()));

        Order newOrder = new Order();
        newOrder.setUser(user);
        newOrder.setTotalPrice(totalPrice);
        newOrder.setStatus(Order.Status.PENDING);
        newOrder.setCreatedAt(LocalDateTime.now());

        OrderItem item = new OrderItem(product, dto.quantity(), product.getPrice(), newOrder);
        
        newOrder.getItems().add(item);

        Order savedOrder = orderRepository.save(newOrder);

        return convertToDTO(savedOrder);
    }

    @Transactional
    public OrderResponseDTO addProductToOrder(UUID orderId, OrderRequestDTO dto, User user){
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new BusinessException("Pedido não encontrado"));
        
        if (!order.getUser().getId().equals(user.getId())) {
            throw new BusinessException("Acesso negado ao pedido");
        }

        var product = productService.findById(dto.productId());

        if (product.getStockQuantity() < dto.quantity()) {
            throw new BusinessException("Estoque insuficiente para: " + product.getName());
        }

        product.setStockQuantity(product.getStockQuantity() - dto.quantity());
        productService.update(product);

        OrderItem existingItem = order.getItems().stream()
            .filter(item -> item.getProduct().getId().equals(product.getId()))
            .findFirst()
            .orElse(null);

        if (existingItem != null) {
            existingItem.setQuantity(existingItem.getQuantity() + dto.quantity());
            existingItem.setPrice(product.getPrice());
        } else { 
            OrderItem item = new OrderItem(product, dto.quantity(), product.getPrice(), order);
            order.getItems().add(item);
        }

        BigDecimal newTotal = addTotalPrice(order.getItems());
        order.setTotalPrice(newTotal);

        Order updatedOrder = orderRepository.save(order);

        return convertToDTO(updatedOrder);
    }

    @Transactional
    public OrderResponseDTO removeProductFromOrder(UUID orderId, OrderRequestDTO dto, User user){
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new BusinessException("Pedido não encontrado"));

        if (!order.getUser().getId().equals(user.getId())) {
            throw new BusinessException("Acesso negado ao pedido");
        }

        OrderItem existingItem = order.getItems().stream()
            .filter(item -> item.getProduct().getId().equals(dto.productId()))
            .findFirst()
            .orElseThrow(() -> new BusinessException("Produto não encontrado no pedido"));

        if (existingItem.getQuantity() < dto.quantity()) {
            throw new BusinessException("Quantidade a remover é maior que a quantidade no pedido");
        }

        existingItem.setQuantity(existingItem.getQuantity() - dto.quantity());
        existingItem.setPrice(existingItem.getProduct().getPrice());

        if (existingItem.getQuantity() == 0) {
            order.getItems().remove(existingItem);
        }

        BigDecimal newTotal = decreaseTotalPrice(order.getItems());
        order.setTotalPrice(newTotal);

        Order updatedOrder = orderRepository.save(order);

        return convertToDTO(updatedOrder);
    }

    private BigDecimal addTotalPrice(List<OrderItem> items) {
        return items.stream()
            .map(item -> item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private BigDecimal decreaseTotalPrice(List<OrderItem> items) {
        return items.stream()
            .map(item -> item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
            .reduce(BigDecimal.ZERO, BigDecimal::subtract);
    }

    private OrderResponseDTO convertToDTO(Order order) {
        List<OrderItemResponseDTO> items = order.getItems().stream()
            .map(item -> new OrderItemResponseDTO(
                item.getProduct().getId(),
                item.getProduct().getName(),
                item.getPrice(),
                item.getQuantity()
            ))
            .toList();

        return new OrderResponseDTO(
            order.getId(),
            order.getStatus().name(),
            order.getTotalPrice(),
            order.getCreatedAt(),
            items
        );
    }
}
