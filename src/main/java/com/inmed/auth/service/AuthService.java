package com.inmed.auth.service;

import com.inmed.auth.dto.ActiveSessionResponse;
import com.inmed.auth.dto.AuthResponse;
import com.inmed.auth.dto.LoginRequest;
import com.inmed.auth.entity.RefreshToken;
import com.inmed.exception.custom.InvalidRefreshTokenException;
import com.inmed.security.JwtService;
import com.inmed.security.blacklist.JwtBlacklistService;
import com.inmed.user.entity.User;
import com.inmed.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public AuthResponse login(
            LoginRequest request,
            String ip
    ){
        return loginService.login(
                request,
                ip
        );
    }
    public AuthResponse refreshToken(
            String refreshToken,
            String ip,
            String device
    ){
        RefreshToken current =
                refreshTokenService
                        .findValidToken(refreshToken);
        User user =
                current.getUser();
        RefreshToken newRefreshToken =
                refreshTokenService.rotate(
                        current,
                        ip,
                        device
                );
        String accessToken =
                jwtService.generateToken(
                        user.getUsername(),
                        user.getRole().name()
                );
        return AuthResponse.builder()
                .accessToken(
                        accessToken
                )
                .refreshToken(
                       newRefreshToken.getToken()
                )
                .username(
                        user.getUsername()
                )
                .role(
                        user.getRole().name()
                )
                .build();
    }
    public void logout(
            String refreshToken,
            String accessToken
    ){
        sessionService.logout(
                refreshToken
        );
        long expiration =
                jwtService.getExpiration(
                        accessToken
                );
        jwtBlacklistService.blacklistToken(
                accessToken,
                expiration
        );
    }

    public List<ActiveSessionResponse> getActiveSessions(){
        return sessionService
                .getActiveSessions();
    }

    public void forceLogout(
            String username
    ){
        User user =
                userRepository
                        .findByUsername(username)
                        .orElseThrow(
                                () ->
                                        new RuntimeException(
                                                "User not found"
                                        )
                        );
        sessionService
                .forceLogout(user);
    }
}