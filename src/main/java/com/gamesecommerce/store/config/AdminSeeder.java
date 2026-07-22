package com.gamesecommerce.store.config;

import com.gamesecommerce.store.model.Role;
import com.gamesecommerce.store.model.User;
import com.gamesecommerce.store.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AdminSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AdminSeeder(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {

        boolean adminExists = userRepository.existsByEmail("admin@gamestore.com");

        if (!adminExists) {
            User admin = new User();
            admin.setUsername("admin");
            admin.setEmail("admin@gamestore.com");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setRole(Role.ADMIN);

            userRepository.save(admin);

            System.out.println("✔ Admin user created!");
        }
    }
}