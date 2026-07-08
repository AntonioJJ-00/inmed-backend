package com.inmed.auth.service;

import com.inmed.auth.dto.*;
import com.inmed.security.JwtService;
import com.inmed.security.blacklist.JwtBlacklistService;
import com.inmed.user.entity.User;
import com.inmed.user.repository.UserRepository;
import com.inmed.exception.custom.InvalidRefreshTokenException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final LoginService loginService;
    private final SessionService sessionService;
    private final RefreshTokenService refreshTokenService;
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final JwtBlacklistService jwtBlacklistService;

    public AuthResponse login(LoginRequest request, String ip) {
        return loginService.login(request, ip);
    }

    public AuthResponse refreshToken(String refreshToken) {

        var stored = refreshTokenService.findByToken(refreshToken);

        if (!refreshTokenService.isValid(stored)) {
            throw new InvalidRefreshTokenException("Refresh token expired");
        }

        User user = stored.getUser();

        String newAccessToken = jwtService.generateToken(
                user.getUsername(),
                user.getRole().name()
        );

        return AuthResponse.builder()
                .accessToken(newAccessToken)
                .refreshToken(stored.getToken())
                .username(user.getUsername())
                .role(user.getRole().name())
                .build();
    }

    public void logout(String refreshToken, String accessToken) {

        sessionService.logout(refreshToken);
        long ttl = jwtService.getExpiration(accessToken);
        jwtBlacklistService.blacklistToken(accessToken, ttl);
    }

    public List<ActiveSessionResponse> getActiveSessions() {
        return sessionService.getActiveSessions();
    }

    public void forceLogout(String username) {

        User user = userRepository.findByUsername(username)
                .orElseThrow();

        sessionService.forceLogout(user);
    }
}