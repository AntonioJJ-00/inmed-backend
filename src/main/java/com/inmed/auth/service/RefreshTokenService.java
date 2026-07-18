package com.inmed.auth.service;

import com.inmed.auth.audit.enums.SecurityEventType;
import com.inmed.auth.audit.service.AuthSecurityAuditService;
import com.inmed.auth.entity.RefreshToken;
import com.inmed.auth.repository.RefreshTokenRepository;
import com.inmed.exception.custom.InvalidRefreshTokenException;
import com.inmed.user.entity.User;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final RefreshTokenRepository repository;
    private final AuthSecurityAuditService authSecurityAuditService;

    /**
     * Crear refresh token inicial
     * <p>
     * Cada refresh token representa una sesión activa.
     */
    @Transactional
    public RefreshToken createRefreshToken(
            User user,
            String ip,
            String device
    ) {
        RefreshToken token =
                RefreshToken.builder()
                        .user(user)
                        .token(
                                UUID.randomUUID()
                                        .toString()
                        )
                        /**
                         * Identificador permanente
                         * de la sesión.
                         */
                        .sessionId(
                                UUID.randomUUID()
                                        .toString()
                        )
                        .clientType(
                                "WEB"
                        )
                        .userAgent(
                                device
                        )
                        /**
                         * Familia para detectar
                         * reutilización de tokens.
                         */
                        .familyId(
                                UUID.randomUUID()
                                        .toString()
                        )
                        .version(1)
                        .expiryDate(
                                LocalDateTime.now()
                                        .plusDays(7)
                        )
                        .ipAddress(ip)
                        .deviceInfo(device)
                        .lastUsedAt(
                                LocalDateTime.now()
                        )
                        .build();
        return repository.save(token);
    }

    /**
     * Buscar refresh token incluyendo usuario
     */
    @Transactional(readOnly = true)
    public RefreshToken findByToken(
            String token
    ) {
        return repository
                .findByTokenWithUser(token)
                .orElseThrow(
                        () ->
                                new InvalidRefreshTokenException(
                                        "Refresh token not found"
                                )
                );
    }

    /**
     * Validación completa
     */
    @Transactional(readOnly = true)
    public RefreshToken findValidToken(
            String token
    ) {
        RefreshToken refreshToken =
                findByToken(token);
        if (refreshToken.isRevoked()) {
            throw new InvalidRefreshTokenException(
                    "Refresh token revoked"
            );
        }
        if (refreshToken.getExpiryDate()
                .isBefore(LocalDateTime.now())) {
            throw new InvalidRefreshTokenException(
                    "Refresh token expired"
            );
        }
        return refreshToken;
    }

    /**
     * Rotación de refresh token
     * <p>
     * El token anterior queda invalidado.
     * <p>
     * La sesión continúa siendo la misma.
     */
    @Transactional
    public RefreshToken rotate(
            RefreshToken oldToken,
            String ip,
            String device
    ) {
        /*
         * Invalidar token anterior
         */
        oldToken.setRevoked(true);
        oldToken.setRevokedAt(
                LocalDateTime.now()
        );
        oldToken.setLastUsedAt(
                LocalDateTime.now()
        );
        repository.save(oldToken);
        /*
         * Crear nuevo refresh token
         *
         * Mantiene:
         *
         * - Usuario
         * - Sesión
         * - Familia
         *
         * Cambia:
         *
         * - Token
         * - Versión
         */
        RefreshToken newToken =
                RefreshToken.builder()
                        .user(
                                oldToken.getUser()
                        )
                        .token(
                                UUID.randomUUID()
                                        .toString()
                        )
                        .sessionId(
                                oldToken.getSessionId()
                        )
                        .clientType(
                                oldToken.getClientType()
                        )
                        .userAgent(
                                device
                        )
                        .familyId(
                                oldToken.getFamilyId()
                        )
                        .version(
                                oldToken.getVersion() + 1
                        )
                        .expiryDate(
                                LocalDateTime.now()
                                        .plusDays(7)
                        )
                        .ipAddress(ip)
                        .deviceInfo(device)
                        .lastUsedAt(
                                LocalDateTime.now()
                        )
                        .build();
        RefreshToken saved =
                repository.save(newToken);


        authSecurityAuditService.log(
                SecurityEventType.REFRESH_TOKEN_USED,
                newToken.getUser(),
                ip,
                device,
                newToken.getSessionId(),
                "{\"version\":"
                        + newToken.getVersion()
                        + "}"
        );
        return saved;
    }

    /**
     * Revocar una sesión específica
     */
    @Transactional
    public void revoke(
            RefreshToken token
    ) {
        token.setRevoked(true);
        token.setRevokedAt(
                LocalDateTime.now()
        );
        repository.save(token);
        authSecurityAuditService.log(
                SecurityEventType.SESSION_REVOKED,
                token.getUser(),
                token.getIpAddress(),
                token.getDeviceInfo(),
                token.getSessionId(),
                "{\"reason\":\"TOKEN_REVOKED\"}"
        );
    }

    /**
     * Cerrar todas las sesiones
     * de un usuario.
     */
    @Transactional
    public void revokeAll(
            User user
    ) {
        List<RefreshToken> tokens =
                repository.findByUser(user);
        tokens.forEach(token -> {
            if (!token.isRevoked()) {
                token.setRevoked(true);
                token.setRevokedAt(
                        LocalDateTime.now()
                );
            }
        });
        repository.saveAll(tokens);
    }

    /**
     * Obtener todos los registros
     */
    @Transactional(readOnly = true)
    public List<RefreshToken> findAll() {
        return repository.findAllWithUser();
    }
}