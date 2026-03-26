package com.gamesecommerce.store.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gamesecommerce.store.model.User;
import com.gamesecommerce.store.repository.UserRepository;

import org.springframework.security.crypto.password.PasswordEncoder;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User findByUsernameOrEmail(String username, String email) {
        return userRepository.findByUsernameOrEmail(username, email);
    }

    public User create(User user) {
        if (findByUsernameOrEmail(user.getUsername(), user.getEmail()) != null) {
            throw new RuntimeException("User with the same username or email already exists.");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public void deleteById(UUID id) {
        userRepository.deleteById(id);
    }

    public User edit(User user) {
        User existingUser = userRepository.findById(user.getId()).orElseThrow(() -> new RuntimeException("User not found with id: " + user.getId()));
        
        if (!existingUser.getUsername().equals(user.getUsername()) && findByUsername(user.getUsername()) != null) {
            throw new RuntimeException("Username '" + user.getUsername() + "' is already taken.");
        }
        
        if (!existingUser.getEmail().equals(user.getEmail()) && findByEmail(user.getEmail()) != null) {
            throw new RuntimeException("Email '" + user.getEmail() + "' is already in use.");
        }

        existingUser.setUsername(user.getUsername());
        existingUser.setEmail(user.getEmail());
        if (user.getPassword() != null && !user.getPassword().isEmpty()) {
            existingUser.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        
        return userRepository.save(existingUser);
    }
}
