package com.inmed.auth.service;

import com.inmed.auth.dto.ActiveSessionResponse;
import com.inmed.auth.entity.RefreshToken;
import com.inmed.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.inmed.security.blacklist.JwtBlacklistService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SessionService {

    private final RefreshTokenService refreshTokenService;
    private final JwtBlacklistService jwtBlacklistService;

    public List<ActiveSessionResponse> getActiveSessions() {

        return refreshTokenService.findAll()
                .stream()
                .map(token -> ActiveSessionResponse.builder()
                        .username(token.getUser().getUsername())
                        .role(token.getUser().getRole().name())
                        .expiresAt(token.getExpiryDate())
                        .build())
                .toList();
    }

    public void forceLogout(User user) {
        refreshTokenService.deleteByUser(user);
    }

    public void logout(String refreshToken) {

        RefreshToken token = refreshTokenService.findByToken(refreshToken);
        refreshTokenService.delete(token);
    }
}