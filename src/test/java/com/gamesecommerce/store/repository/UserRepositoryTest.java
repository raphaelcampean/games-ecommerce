package com.gamesecommerce.store.repository;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import com.gamesecommerce.store.config.AbstractPostgresContainerTest;
import com.gamesecommerce.store.model.User;

import jakarta.persistence.EntityManager;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class UserRepositoryTest extends AbstractPostgresContainerTest {
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private EntityManager entityManager;

    @Test
    @DisplayName("Should find user by username")
    public void testFindByUsername() {
        User user = createUser();
        entityManager.flush();
        entityManager.clear();

        User found = userRepository.findByUsername(user.getUsername()).orElse(null);

        assertThat(found).isNotNull();
        assertThat(found.getUsername()).isEqualTo(user.getUsername());
    }

    @Test
    @DisplayName("Should find user by email")
    public void testFindByEmail() {
        User user = createUser();

        entityManager.flush();
        entityManager.clear();

        User found = userRepository.findByEmail(user.getEmail()).orElse(null);

        assertThat(found).isNotNull();
        assertThat(found.getEmail()).isEqualTo(user.getEmail());
    }

    @Test
    @DisplayName("Should find user by username or email")
    public void testFindByUsernameOrEmail() {
        User user = createUser();

        entityManager.flush();
        entityManager.clear();

        User foundByUsername = userRepository.findByUsernameOrEmail(user.getUsername(), user.getEmail()).orElse(null);
        User foundByEmail = userRepository.findByUsernameOrEmail(user.getEmail(), user.getEmail()).orElse(null);

        assertThat(foundByUsername).isNotNull();
        assertThat(foundByEmail).isNotNull();
    }

    private User createUser() {
        User user = new User();
        user.setUsername("testuser");
        user.setEmail("test@gmail.com");
        user.setPassword("password");
        return userRepository.save(user);
    }
}
