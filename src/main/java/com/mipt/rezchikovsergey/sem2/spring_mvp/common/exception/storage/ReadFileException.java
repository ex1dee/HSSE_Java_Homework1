package com.mipt.rezchikovsergey.sem2.spring_mvp.common.exception.storage;

public class ReadFileException extends StorageException {

  public ReadFileException(String filename) {
    this(filename, null);
  }

  public ReadFileException(String filename, Throwable cause) {
    super("Failed to read file " + filename, cause);
  }
}
