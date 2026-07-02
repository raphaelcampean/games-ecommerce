package com.gamesecommerce.store.repository;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

import jakarta.persistence.EntityManager;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.gamesecommerce.store.config.AbstractPostgresContainerTest;
import com.gamesecommerce.store.model.Order;
import com.gamesecommerce.store.model.OrderItem;
import com.gamesecommerce.store.model.Product;
import com.gamesecommerce.store.model.User;

@SpringBootTest
@ActiveProfiles("test")
class OrderRepositoryTest extends AbstractPostgresContainerTest {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private EntityManager entityManager;

    @Test
    @DisplayName("Should find orders by user ID")
    void testFindByUserId() {
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
    void testFindByUserIdNoOrders() {
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

    private Product createProduct() {
        Product product = new Product();
        product.setName("Test Game");
        product.setDescription("A test game description");
        product.setSlug("test-game");
        product.setPrice(new BigDecimal("59.99"));

        entityManager.persist(product);
        entityManager.flush();
        return product;
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
}