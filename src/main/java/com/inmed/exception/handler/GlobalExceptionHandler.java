package com.inmed.exception.handler;

import com.inmed.common.response.ApiErrorResponse;
import com.inmed.exception.custom.DuplicateResourceException;
import com.inmed.exception.custom.InvalidRefreshTokenException;
import com.inmed.exception.custom.ResourceNotFoundException;
import com.inmed.exception.custom.UserBlockedException; // Importamos la nueva excepción
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // NUEVO MÉTODO AÑADIDO: Captura el bloqueo de usuario
    @ExceptionHandler(UserBlockedException.class)
    public ResponseEntity<ApiErrorResponse> handleUserBlocked(
            UserBlockedException ex
    ) {
        ApiErrorResponse response =
                ApiErrorResponse.builder()
                        .status(HttpStatus.FORBIDDEN.value()) // Código 403
                        .message(ex.getMessage())             // "User is blocked"
                        .timestamp(LocalDateTime.now())
                        .build();

        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(response);
    }

    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<ApiErrorResponse> handleDuplicateResource(
            DuplicateResourceException ex
    ) {
        ApiErrorResponse response =
                ApiErrorResponse.builder()
                        .status(HttpStatus.CONFLICT.value())
                        .message(ex.getMessage())
                        .timestamp(LocalDateTime.now())
                        .build();
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(response);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleNotFound(
            ResourceNotFoundException ex
    ) {
        ApiErrorResponse response =
                ApiErrorResponse.builder()
                        .status(HttpStatus.NOT_FOUND.value())
                        .message(ex.getMessage())
                        .timestamp(LocalDateTime.now())
                        .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(response);
    }

    @ExceptionHandler(InvalidRefreshTokenException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ApiErrorResponse handleInvalidRefreshToken(
            InvalidRefreshTokenException ex
    ) {
        return ApiErrorResponse.builder()
                .status(401)
                .message(ex.getMessage())
                .timestamp(LocalDateTime.now())
                .build();
    }
}