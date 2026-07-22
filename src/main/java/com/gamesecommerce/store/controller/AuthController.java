package com.gamesecommerce.store.controller;

import com.gamesecommerce.store.record.AuthResponseDTO;
import com.gamesecommerce.store.record.LoginRequestDTO;
import com.gamesecommerce.store.record.UserDTO;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.gamesecommerce.store.model.User;
import com.gamesecommerce.store.record.LoginResponseDTO;
import com.gamesecommerce.store.service.TokenService;

@RestController
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(
            @RequestBody @Validated LoginRequestDTO data
    ) {

        var usernamePassword =
                new UsernamePasswordAuthenticationToken(
                        data.login(),
                        data.password()
                );

        var auth = authenticationManager.authenticate(usernamePassword);

        var user = (User) auth.getPrincipal();

        var token = tokenService.generateToken(user);

        return ResponseEntity.ok(
                new AuthResponseDTO(
                        new UserDTO(user),
                        token
                )
        );
    }
}
