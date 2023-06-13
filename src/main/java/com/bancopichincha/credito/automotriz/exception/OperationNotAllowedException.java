package com.bancopichincha.credito.automotriz.exception;

public class OperationNotAllowedException extends RuntimeException{

  public OperationNotAllowedException(String message) {
    super(message);
  }
}
