package com.gamesecommerce.store.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.gamesecommerce.store.model.User;
import com.gamesecommerce.store.record.UserRequestDTO;
import com.gamesecommerce.store.repository.UserRepository;
import com.gamesecommerce.store.service.UserService;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @Test
    @DisplayName("Should create user successfully with encoded password")
    void create_Success() {
        UserRequestDTO dto = new UserRequestDTO(
                "raphael",
                "raphael@example.com",
                "secret123",
                "raphael"
        );

        when(userRepository.findByUsernameOrEmail(
                dto.username(),
                dto.email()
        )).thenReturn(Optional.empty());

        when(passwordEncoder.encode("secret123"))
                .thenReturn("encoded_hash");

        when(userRepository.save(any(User.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        User savedUser = userService.create(dto);

        assertThat(savedUser).isNotNull();
        assertThat(savedUser.getPassword()).isEqualTo("encoded_hash");

        verify(userRepository, times(1))
                .save(any(User.class));
    }

    @Test
    @DisplayName("Should throw exception when user already exists")
    void create_ThrowsException() {
        UserRequestDTO dto = new UserRequestDTO(
                "exists",
                "exists@email.com",
                "secret123",
                "exists"
        );

        when(userRepository.findByUsernameOrEmail(
                dto.username(),
                dto.email()
        )).thenReturn(Optional.of(new User()));

        assertThatThrownBy(() -> userService.create(dto))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("User with the same username or email already exists.");

        verify(userRepository, never())
                .save(any());
    }

    @Test
    @DisplayName("Should edit user password only if provided")
    void edit_UpdatePassword() {
        UUID id = UUID.randomUUID();

        User existingUser = new User();
        existingUser.setId(id);
        existingUser.setUsername("old");
        existingUser.setEmail("old@email.com");
        existingUser.setPassword("old_hash");

        UserRequestDTO dto = new UserRequestDTO(
                "new",
                "new@email.com",
                "new_pass",
                "new"
        );

        when(userRepository.findById(id))
                .thenReturn(Optional.of(existingUser));

        when(userRepository.findByUsername("new"))
                .thenReturn(Optional.empty());

        when(userRepository.findByEmail("new@email.com"))
                .thenReturn(Optional.empty());

        when(passwordEncoder.encode("new_pass"))
                .thenReturn("new_hash");

        when(userRepository.save(any(User.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        User result = userService.edit(existingUser, dto);

        assertThat(result.getUsername())
                .isEqualTo("new");

        assertThat(result.getPassword())
                .isEqualTo("new_hash");
    }
}