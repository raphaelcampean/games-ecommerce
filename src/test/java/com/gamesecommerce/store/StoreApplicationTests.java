package com.gamesecommerce.store;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test") // Mantém apenas esse cara para ativar o perfil
class StoreApplicationTests {

    @Test
    void contextLoads() {
    }

}