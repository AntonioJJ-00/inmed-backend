package com.inmed.sync.retry;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class RetryPolicy {

    public LocalDateTime nextRetry(
            int retryCount
    ){
        return switch (retryCount){
            case 0 ->
                    LocalDateTime.now().plusSeconds(30);
            case 1 ->
                    LocalDateTime.now().plusMinutes(1);
            case 2 ->
                    LocalDateTime.now().plusMinutes(5);
            case 3 ->
                    LocalDateTime.now().plusMinutes(15);
            default ->
                    LocalDateTime.now().plusHours(1);
        };
    }
}