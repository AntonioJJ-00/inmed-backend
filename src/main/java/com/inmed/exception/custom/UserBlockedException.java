package com.inmed.exception.custom;

public class UserBlockedException extends RuntimeException {
  public UserBlockedException(String message) {
    super(message);
  }
}