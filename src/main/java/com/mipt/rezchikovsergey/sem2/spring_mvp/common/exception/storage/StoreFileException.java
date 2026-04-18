package com.mipt.rezchikovsergey.sem2.spring_mvp.common.exception.storage;

public class StoreFileException extends StorageException {
  public StoreFileException(String filename, Throwable cause) {
    super("Failed to store file " + filename, cause);
  }
}
