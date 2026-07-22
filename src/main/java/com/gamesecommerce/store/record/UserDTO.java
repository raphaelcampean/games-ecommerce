package com.gamesecommerce.store.record;

import com.gamesecommerce.store.model.User;
import com.gamesecommerce.store.model.Role;
import java.time.LocalDateTime;
import java.util.UUID;

public record UserDTO(
        UUID id,
        String username,
        String email,
        Role role,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public UserDTO(User user) {
        this(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getRole(),
                user.getCreatedAt(),
                user.getUpdatedAt()
        );
    }
}