package com.gamesecommerce.store.repository;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import com.gamesecommerce.store.model.Order;
import com.gamesecommerce.store.model.OrderItem;
import com.gamesecommerce.store.model.Product;
import com.gamesecommerce.store.model.User;
import com.gamesecommerce.store.repository.OrderRepository;
import static org.assertj.core.api.Assertions.assertThat;

import jakarta.persistence.EntityManager;

@DataJpaTest
@ActiveProfiles("test")
public class OrderRepositoryTest {
    @Autowired
    EntityManager entityManager;

    @Autowired
    OrderRepository orderRepository;
    
    @Test
    @DisplayName("Should find orders by user ID")
    public void testFindByUserId() {
        User user = createUser();
        Product product = createProduct();
        createOrder(user, product);

        entityManager.flush();
        entityManager.clear();

        List<Order> orders = orderRepository.findByUserId(user.getId());

        assertThat(orders).hasSize(1);
        assertThat(orders.get(0).getUser().getId()).isEqualTo(user.getId());
    }

    @Test
    @DisplayName("Should return empty list when no orders for user ID")
    public void testFindByUserIdNoOrders() {
        User user = createUser();
    
        List<Order> orders = orderRepository.findByUserId(user.getId());

        assertThat(orders).isEmpty();
    }

    private User createUser() {
        User user = new User();
        user.setUsername("johndoe");
        user.setEmail("john.doe@example.com");
        user.setPassword("password");
        entityManager.persist(user);
        entityManager.flush();
        return user;
    }

    private Order createOrder(User user, Product product) {
        Order order = new Order();
        order.setUser(user);
        order.setTotalPrice(product.getPrice());
        order.setStatus(Order.Status.PENDING);
        
        entityManager.persist(order); 

        OrderItem item = createOrderItem(order, product);
        
        order.setItems(List.of(item));
        
        entityManager.flush();
        return order;
    }

    private OrderItem createOrderItem(Order order, Product product) {
        OrderItem item = new OrderItem();
        item.setOrder(order);
        item.setProduct(product);
        item.setQuantity(1);
        item.setPrice(product.getPrice());
        
        entityManager.persist(item);
        return item;
    }

    private Product createProduct() {
        Product product = new Product();
        product.setName("Test Game");
        product.setDescription("A test game description");
        BigDecimal price = new BigDecimal("59.99");
        product.setPrice(price);
        entityManager.persist(product);
        entityManager.flush();
        return product;
    }
}
