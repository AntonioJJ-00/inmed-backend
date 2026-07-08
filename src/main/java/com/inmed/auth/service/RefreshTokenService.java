package com.inmed.auth.service;

import com.inmed.auth.entity.RefreshToken;
import com.inmed.auth.repository.RefreshTokenRepository;
import com.inmed.exception.custom.InvalidRefreshTokenException;
import com.inmed.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;


    @Transactional
    public RefreshToken createRefreshToken(User user) {

        refreshTokenRepository.deleteByUser(user);

        refreshTokenRepository.flush();

        RefreshToken refreshToken = RefreshToken.builder()
                .user(user)
                .token(UUID.randomUUID().toString())
                .expiryDate(LocalDateTime.now().plusDays(7))
                .familyId(UUID.randomUUID().toString()) // ✅ FIX
                .version(1) // ✅ FIX
                .revoked(false)
                .build();

        return refreshTokenRepository.save(refreshToken);
    }

    // ✅ FIX CRÍTICO: fetch join para evitar LazyInitializationException
    @Transactional(readOnly = true)
    public RefreshToken findByToken(String token) {

        return refreshTokenRepository
                .findByTokenWithUser(token)
                .orElseThrow(() ->
                        new InvalidRefreshTokenException("Refresh token not found")
                );
    }

    @Transactional(readOnly = true)
    public boolean isValid(RefreshToken refreshToken) {
        return refreshToken.getExpiryDate().isAfter(LocalDateTime.now());
    }

    @Transactional
    public void delete(RefreshToken refreshToken) {
        refreshTokenRepository.delete(refreshToken);
    }

    @Transactional
    public void deleteByUser(User user) {
        refreshTokenRepository.deleteByUser(user);
    }

    @Transactional
    public void deleteByToken(String token) {
        refreshTokenRepository.deleteByToken(token);
    }

    @Transactional(readOnly = true)
    public List<RefreshToken> findAll() {
        return refreshTokenRepository.findAllWithUser();
    }

}