package com.hei.model.exception;

public class InvalidAccountNumberException extends RuntimeException {
  public InvalidAccountNumberException(String message) {
    super(message);
  }
}
