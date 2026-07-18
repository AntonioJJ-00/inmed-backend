package com.inmed.auth.service;

import com.inmed.auth.audit.enums.SecurityEventType;
import com.inmed.auth.audit.service.AuthSecurityAuditService;
import com.inmed.auth.dto.ActiveSessionResponse;
import com.inmed.auth.entity.RefreshToken;
import com.inmed.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SessionService {

    private final RefreshTokenService refreshTokenService;
    private final AuthSecurityAuditService authSecurityAuditService;

    public List<ActiveSessionResponse> getActiveSessions() {

        return refreshTokenService.findAll()
                .stream()
                .filter(token -> !token.isRevoked())
                .map(token ->
                        ActiveSessionResponse.builder()
                                .userId(token.getUser().getId())
                                .sessionId(token.getSessionId())
                                .username(token.getUser().getUsername())
                                .role(token.getUser().getRole().name())
                                .ipAddress(token.getIpAddress())
                                .device(token.getDeviceInfo())
                                .clientType(token.getClientType())
                                .version(token.getVersion())
                                .loginAt(token.getCreatedAt())
                                .lastUsedAt(token.getLastUsedAt())
                                .expiresAt(token.getExpiryDate())
                                .build()
                )
                .toList();
    }

    public void forceLogout(
            User user
    ){
        refreshTokenService
                .revokeAll(user);
        authSecurityAuditService.log(
                SecurityEventType.FORCE_LOGOUT,
                user,
                null,
                null,
                null,
                "{\"reason\":\"ADMIN_ACTION\"}"
        );
    }

    public void logout(
            String refreshToken
    ){
        RefreshToken token =
                refreshTokenService
                        .findByToken(refreshToken);
        refreshTokenService
                .revoke(token);
        authSecurityAuditService.log(
                SecurityEventType.LOGOUT,
                token.getUser(),
                token.getIpAddress(),
                token.getDeviceInfo(),
                token.getSessionId(),
                "{\"type\":\"USER_LOGOUT\"}"
        );
    }
}