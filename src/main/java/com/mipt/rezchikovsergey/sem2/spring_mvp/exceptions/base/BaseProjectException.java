package com.mipt.rezchikovsergey.sem2.spring_mvp.exceptions.base;

public class BaseProjectException extends RuntimeException {
  public BaseProjectException(String message) {
    super(message);
  }

  public BaseProjectException(String message, Throwable cause) {
    super(message, cause);
  }
}
