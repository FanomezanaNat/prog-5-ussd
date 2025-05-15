package com.hei.model.exception;

public class InvalidAmountException extends RuntimeException {
  public InvalidAmountException(String message) {
    super(message);
  }
}
