package com.inmed.exception.custom;

public class TooManyRequestsException
        extends RuntimeException {

    public TooManyRequestsException(String message) {
        super(message);
    }

}