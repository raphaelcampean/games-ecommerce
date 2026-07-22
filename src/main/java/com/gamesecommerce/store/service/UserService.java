package com.gamesecommerce.store.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.gamesecommerce.store.model.Role;
import com.gamesecommerce.store.record.ProductDTO;
import com.gamesecommerce.store.record.UserDTO;
import com.gamesecommerce.store.record.UserRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.gamesecommerce.store.model.User;
import com.gamesecommerce.store.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Page<UserDTO> getUsers(Pageable pageable) {
        return userRepository.findAll(pageable)
                .map(UserDTO::new);
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public Optional<User> findByUsernameOrEmail(String username, String email) {
        return userRepository.findByUsernameOrEmail(username, email);
    }

    public User create(UserRequestDTO data) {
        if (findByUsernameOrEmail(data.username(), data.email()).isPresent()) {
            throw new RuntimeException("User with the same username or email already exists.");
        }

        User user = new User();

        user.setUsername(data.username());
        user.setEmail(data.email());
        user.setName(data.name());
        user.setPassword(passwordEncoder.encode(data.password()));
        user.setRole(Role.USER);

        return userRepository.save(user);
    }

    public void deleteById(UUID id) {
        userRepository.deleteById(id);
    }

    public User edit(User user, UserRequestDTO dto) {
        User existingUser = userRepository.findById(user.getId())
                .orElseThrow(() -> new RuntimeException("User not found with id: " + user.getId()));

        if (!existingUser.getUsername().equals(dto.username())
                && findByUsername(dto.username()).isPresent()) {
            throw new RuntimeException("Username '" + dto.username() + "' is already taken.");
        }

        if (!existingUser.getEmail().equals(dto.email())
                && findByEmail(dto.email()).isPresent()) {
            throw new RuntimeException("Email '" + dto.email() + "' is already in use.");
        }

        existingUser.setUsername(dto.username());
        existingUser.setEmail(dto.email());
        existingUser.setName(dto.name());

        if (dto.password() != null && !dto.password().isEmpty()) {
            existingUser.setPassword(passwordEncoder.encode(dto.password()));
        }

        return userRepository.save(existingUser);
    }

    public User findByLogin(String login) {
        return userRepository.findByUsername(login)
                .or(() -> userRepository.findByEmail(login))
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}