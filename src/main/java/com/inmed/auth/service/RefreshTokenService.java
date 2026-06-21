package com.inmed.auth.service;

import com.inmed.auth.entity.RefreshToken;
import com.inmed.auth.repository.RefreshTokenRepository;
import com.inmed.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    public RefreshToken createRefreshToken(
            User user
    ) {

        refreshTokenRepository
                .findByUser(user)
                .ifPresent(refreshTokenRepository::delete);

        RefreshToken refreshToken =
                RefreshToken.builder()
                        .token(
                                UUID.randomUUID()
                                        .toString()
                        )
                        .user(user)
                        .expiryDate(
                                LocalDateTime.now()
                                        .plusDays(7)
                        )
                        .build();

        return refreshTokenRepository
                .save(refreshToken);
    }

    public boolean isValid(
            RefreshToken refreshToken
    ) {

        return refreshToken
                .getExpiryDate()
                .isAfter(
                        LocalDateTime.now()
                );
    }

    public void deleteByUser(
            User user
    ) {

        refreshTokenRepository
                .deleteByUser(user);
    }
}