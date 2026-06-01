package com.gamesecommerce.store;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.gamesecommerce.store.config.AbstractPostgresContainerTest;

@Testcontainers
@SpringBootTest
@ActiveProfiles("test")
class StoreApplicationTests extends AbstractPostgresContainerTest {
    @Test
    void contextLoads() {
    }
}