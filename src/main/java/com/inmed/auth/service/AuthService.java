package com.inmed.auth.service;

import com.inmed.auth.dto.AuthResponse;
import com.inmed.auth.dto.LoginRequest;
import com.inmed.auth.entity.RefreshToken;
import com.inmed.exception.custom.InvalidRefreshTokenException;
import com.inmed.exception.custom.ResourceNotFoundException;
import com.inmed.security.JwtService;
import com.inmed.user.entity.User;
import com.inmed.user.repository.UserRepository;
import com.inmed.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;

import com.inmed.auth.dto.ActiveSessionResponse;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final RefreshTokenService refreshTokenService;

    private final JwtService jwtService;

    private final LoginAuditService loginAuditService;

    private final UserService userService;

    public AuthResponse login(LoginRequest request,
                              String ipAddress) {

        User user = userRepository
                .findByUsername(request.getUsername())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Invalid username or password"
                        )
                );

        // MODIFICACIÓN AQUÍ: Lanzamos tu excepción personalizada
        if (!Boolean.TRUE.equals(user.getEnabled())) {
            throw new com.inmed.exception.custom.UserBlockedException(
                    "User is blocked"
            );
        }

        boolean matches =
                passwordEncoder.matches(
                        request.getPassword(),
                        user.getPassword()
                );

        if (!matches) {

            userService
                    .increaseFailedAttempts(
                            user
                    );

            loginAuditService.register(
                    request.getUsername(),
                    "FAILED",
                    ipAddress
            );

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

        loginAuditService.register(
                user.getUsername(),
                "SUCCESS",
                ipAddress
        );

        userService.resetFailedAttempts(
                user
        );

        return AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .username(user.getUsername())
                .role(user.getRole().name())
                .build();
    }

    public AuthResponse refreshToken(
            String refreshToken
    ) {

        RefreshToken storedToken =
                refreshTokenService
                        .findByToken(refreshToken);

        if (!refreshTokenService.isValid(storedToken)) {

            throw new InvalidRefreshTokenException(
                    "Refresh token expired"
            );
        }

        User user =
                storedToken.getUser();

        String newAccessToken =
                jwtService.generateToken(
                        user.getUsername(),
                        user.getRole().name()
                );

        return AuthResponse.builder()
                .accessToken(newAccessToken)
                .refreshToken(storedToken.getToken())
                .username(user.getUsername())
                .role(user.getRole().name())
                .build();
    }

    public void logout(
            String refreshToken
    ) {

        RefreshToken storedToken =
                refreshTokenService
                        .findByToken(refreshToken);

        refreshTokenService.delete(storedToken);
    }

    public List<ActiveSessionResponse>
    getActiveSessions() {

        return refreshTokenService
                .findAll()
                .stream()
                .map(token ->
                        ActiveSessionResponse
                                .builder()
                                .username(
                                        token.getUser()
                                                .getUsername()
                                )
                                .role(
                                        token.getUser()
                                                .getRole()
                                                .name()
                                )
                                .expiresAt(
                                        token.getExpiryDate()
                                )
                                .build()
                )
                .toList();
    }

    public void forceLogout(
            String username
    ) {

        User user =
                userRepository
                        .findByUsername(username)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "User not found"
                                )
                        );

        refreshTokenService
                .deleteByUser(user);
    }
}