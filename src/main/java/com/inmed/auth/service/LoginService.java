package com.inmed.auth.service;

import com.inmed.auth.dto.AuthResponse;
import com.inmed.auth.dto.LoginRequest;
import com.inmed.exception.custom.ResourceNotFoundException;
import com.inmed.exception.custom.UserBlockedException;
import com.inmed.security.JwtService;
import com.inmed.user.entity.User;
import com.inmed.user.repository.UserRepository;
import com.inmed.user.service.security.UserSecurityService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;
    private final LoginAuditService loginAuditService;
    private final UserSecurityService userSecurityService;

    public AuthResponse login(LoginRequest request, String ipAddress) {

        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Invalid username or password")
                );

        // 🔐 VALIDAR BLOQUEO (delegado correctamente)
        if (!user.getEnabled()) {

            boolean unlocked = userSecurityService.unlockWhenLockExpired(user);

            if (!unlocked) {
                throw new UserBlockedException("User is blocked");
            }
        }

        // 🔐 PASSWORD CHECK
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {

            userSecurityService.increaseFailedAttempts(user);

            loginAuditService.register(
                    request.getUsername(),
                    "FAILED",
                    ipAddress
            );

            throw new ResourceNotFoundException("Invalid username or password");
        }

        // 🔐 RESET FAILS
        userSecurityService.resetFailedAttempts(user);

        // 🔐 TOKENS
        String accessToken = jwtService.generateToken(
                user.getUsername(),
                user.getRole().name()
        );

        String refreshToken = refreshTokenService
                .createRefreshToken(user)
                .getToken();

        // 📊 AUDIT
        loginAuditService.register(
                user.getUsername(),
                "SUCCESS",
                ipAddress
        );

        return AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .username(user.getUsername())
                .role(user.getRole().name())
                .build();
    }
}