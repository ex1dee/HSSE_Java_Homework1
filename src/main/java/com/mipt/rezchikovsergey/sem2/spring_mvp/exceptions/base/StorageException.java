package com.mipt.rezchikovsergey.sem2.spring_mvp.exceptions.base;

public class StorageException extends RuntimeException {
  public StorageException(String message) {
    super(message);
  }

  public StorageException(String message, Throwable cause) {
    super(message, cause);
  }
}
