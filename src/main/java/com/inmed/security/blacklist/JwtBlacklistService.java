package com.inmed.security.blacklist;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class JwtBlacklistService {

    private final RedisTemplate<String, String> redisTemplate;

    private static final String PREFIX = "blacklist:";

    // 🔐 bloquear token con TTL
    public void blacklistToken(String token, long expirationMillis) {

        redisTemplate.opsForValue().set(
                PREFIX + token,
                "blacklisted",
                Duration.ofMillis(expirationMillis)
        );
    }

    public boolean isBlacklisted(String token) {

        return Boolean.TRUE.equals(
                redisTemplate.hasKey(PREFIX + token)
        );
    }
}