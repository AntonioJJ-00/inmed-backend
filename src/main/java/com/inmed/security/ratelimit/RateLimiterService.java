package com.inmed.security.ratelimit;

import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class RateLimiterService {

    // Máximo de intentos permitidos
    private static final int MAX_REQUESTS = 5;

    // Ventana de tiempo (1 minuto)
    private static final long WINDOW_SECONDS = 60;

    private final Map<String, RequestCounter> requests =
            new ConcurrentHashMap<>();

    public boolean allowRequest(String key) {

        Instant now = Instant.now();

        RequestCounter counter =
                requests.computeIfAbsent(
                        key,
                        k -> new RequestCounter(0, now)
                );

        synchronized (counter) {

            long seconds =
                    now.getEpochSecond()
                            - counter.firstRequestTime.getEpochSecond();

            // Si pasó el minuto, reinicia el contador
            if (seconds >= WINDOW_SECONDS) {

                counter.count = 1;
                counter.firstRequestTime = now;

                return true;
            }

            // Si llegó al límite
            if (counter.count >= MAX_REQUESTS) {
                return false;
            }

            counter.count++;

            return true;
        }
    }

    private static class RequestCounter {

        private int count;

        private Instant firstRequestTime;

        public RequestCounter(
                int count,
                Instant firstRequestTime
        ) {
            this.count = count;
            this.firstRequestTime = firstRequestTime;
        }
    }

}