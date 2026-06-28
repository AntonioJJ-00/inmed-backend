package com.inmed.common.response;

import java.time.LocalDateTime;

public class ResponseFactory {

    private ResponseFactory() {
    }

    public static <T> ApiResponse<T> success(
            String message,
            T data
    ) {

        return ApiResponse.<T>builder()
                .success(true)
                .message(message)
                .data(data)
                .timestamp(LocalDateTime.now())
                .build();
    }

    public static ApiResponse<Void> success(
            String message
    ) {

        return ApiResponse.<Void>builder()
                .success(true)
                .message(message)
                .data(null)
                .timestamp(LocalDateTime.now())
                .build();
    }

    public static ApiErrorResponse error(
            int status,
            String message
    ) {

        return ApiErrorResponse.builder()
                .success(false)
                .status(status)
                .message(message)
                .data(null)
                .timestamp(LocalDateTime.now())
                .build();
    }
}