package com.inmed.exception.custom;

public class InvalidRefreshTokenException
        extends RuntimeException {

    public InvalidRefreshTokenException(
            String message
    ) {
        super(message);
    }
}