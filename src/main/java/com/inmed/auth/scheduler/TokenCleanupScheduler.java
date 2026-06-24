package com.inmed.auth.scheduler;

import com.inmed.auth.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@Component
@RequiredArgsConstructor
public class TokenCleanupScheduler {

    private final RefreshTokenRepository
            refreshTokenRepository;

    @Scheduled(cron = "0 0 * * * *")
    public void cleanExpiredTokens() {

        refreshTokenRepository
                .deleteByExpiryDateBefore(
                        LocalDateTime.now()
                );

        log.info(
                "Expired refresh tokens cleaned"
        );
    }
}