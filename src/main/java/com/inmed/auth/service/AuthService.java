package com.inmed.auth.service;

import com.inmed.auth.dto.AuthResponse;
import com.inmed.auth.dto.LoginRequest;
import com.inmed.exception.custom.ResourceNotFoundException;
import com.inmed.security.JwtService;
import com.inmed.user.entity.User;
import com.inmed.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final RefreshTokenService refreshTokenService;

    private final JwtService jwtService;

    public AuthResponse login(LoginRequest request) {

        User user = userRepository
                .findByUsername(request.getUsername())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Invalid username or password"
                        )
                );

        boolean matches =
                passwordEncoder.matches(
                        request.getPassword(),
                        user.getPassword()
                );

        if (!matches) {

            throw new ResourceNotFoundException(
                    "Invalid username or password"
            );
        }

        String accessToken =
                jwtService.generateToken(
                        user.getUsername(),
                        user.getRole().name()
                );

        String refreshToken =
                refreshTokenService
                        .createRefreshToken(user)
                        .getToken();

        return AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .username(user.getUsername())
                .role(user.getRole().name())
                .build();
    }
}