package com.gamesecommerce.store.service;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gamesecommerce.store.model.Order;
import com.gamesecommerce.store.model.OrderItem;
import com.gamesecommerce.store.model.User;
import com.gamesecommerce.store.record.OrderRequestDTO;
import com.gamesecommerce.store.repository.OrderRepository;

import jakarta.transaction.Transactional;

@Service
public class OrderService {
    @Autowired
    OrderRepository orderRepository;

    @Autowired
    ProductService productService;

    @Transactional
    public Order createOrder(OrderRequestDTO dto, User user) {
        var product = productService.findById(dto.productId());
        
        if (product.getStockQuantity() < dto.quantity()) {
            throw new RuntimeException("Not enough stock for product: " + product.getName());
        }

        product.setStockQuantity(product.getStockQuantity() - dto.quantity());
        productService.update(product.getId(), product);

        BigDecimal totalPrice = product.getPrice().multiply(BigDecimal.valueOf(dto.quantity()));

        Order newOrder = new Order();
        newOrder.setUser(user);
        newOrder.setTotalPrice(totalPrice);
        newOrder.setStatus(Order.Status.PENDING);

        OrderItem item = new OrderItem(product, dto.quantity(), product.getPrice(), newOrder);
        newOrder.getItems().add(item);

        return orderRepository.save(newOrder);
    }
}
