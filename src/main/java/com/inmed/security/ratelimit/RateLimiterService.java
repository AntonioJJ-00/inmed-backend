package com.inmed.security.ratelimit;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class RateLimiterService {

    private final RedisTemplate<String, String> redisTemplate;

    private static final int MAX_REQUESTS = 5;
    private static final long WINDOW_SECONDS = 60;

    public boolean allowRequest(String key) {

        String redisKey = buildKey(key);

        Long requests = redisTemplate.opsForValue().increment(redisKey);
        System.out.println("Rate Limit Filter");
        if (requests == null) {
            return false;
        }

        // primera petición → set TTL
        if (requests == 1) {
            redisTemplate.expire(redisKey, Duration.ofSeconds(WINDOW_SECONDS));
        }

        return requests <= MAX_REQUESTS;
    }

    private String buildKey(String key) {
        return "rate_limit:login:" + key;
    }
}