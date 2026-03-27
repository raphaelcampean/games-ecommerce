package com.gamesecommerce.store.service;

import java.time.Instant;

import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.gamesecommerce.store.exception.UnauthorizedException;
import com.gamesecommerce.store.model.User;

import org.springframework.beans.factory.annotation.Value;

@Service
public class TokenService {
    @Value("${api.security.token.secret}")
    private String secret;

    public String generateToken(User user){
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);

            String token = JWT.create()
                    .withIssuer("store")
                    .withSubject(user.getEmail())
                    .withExpiresAt(generateExpirationDate())
                    .sign(algorithm);
            return token;
        } catch (Exception e) {
            throw new UnauthorizedException("Error generating token: " + e.getMessage());
        }
    }

    public String validateToken(String token){
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            String email = JWT.require(algorithm)
                    .withIssuer("store")
                    .build()
                    .verify(token)
                    .getSubject();
            return email;
        } catch (Exception e) {
            throw new UnauthorizedException("Invalid token: " + e.getMessage());
        }
    }

    public Instant generateExpirationDate() {
        return Instant.now().plusSeconds(3600);
    }
}
