package com.gamesecommerce.store.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.gamesecommerce.store.model.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, UUID> {
    @Query("""
        SELECT o FROM Order o
        LEFT JOIN FETCH o.items
        WHERE o.user.id = :userId
    """)
    List<Order> findByUserId(UUID userId);
}
