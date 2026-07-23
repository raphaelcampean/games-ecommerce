package com.gamesecommerce.store.controller;

import com.gamesecommerce.store.record.AuthResponseDTO;
import com.gamesecommerce.store.record.RegisterResponseDTO;
import com.gamesecommerce.store.record.UserDTO;
import com.gamesecommerce.store.record.UserRequestDTO;
import com.gamesecommerce.store.service.TokenService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.gamesecommerce.store.model.User;
import com.gamesecommerce.store.service.UserService;

@RestController
@RequestMapping("/usuarios")
public class UserController {
    @Autowired
    UserService userService;

    @Autowired
    TokenService tokenService;

    @PostMapping
    public ResponseEntity<AuthResponseDTO> createUser(
            @RequestBody @Validated UserRequestDTO data
    ) {

        User savedUser = userService.create(data);

        String token = tokenService.generateToken(savedUser);

        return ResponseEntity.status(201)
                .body(
                        new AuthResponseDTO(
                                new UserDTO(savedUser),
                                token
                        )
                );
    }

    @GetMapping("/{username}")
    public ResponseEntity<User> getUserByUsername(@PathVariable String username) {
        User user = userService.findByUsername(username)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
            
        return ResponseEntity.ok(user);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> editUser(
            @PathVariable UUID id,
            @RequestBody @Validated UserRequestDTO dto
    ) {
        try {
            User user = new User();
            user.setId(id);

            User updatedUser = userService.edit(user, dto);

            return ResponseEntity.ok(updatedUser);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable UUID id) {
        try {
            userService.deleteById(id);
            return ResponseEntity.ok("User deleted with id: " + id);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error deleting user: " + e.getMessage());
        }
    }

    @GetMapping("/me")
    public ResponseEntity<UserDTO> me(Authentication authentication){
        User user = (User) authentication.getPrincipal();

        return ResponseEntity.ok(
                new UserDTO(user)
        );
    }
}