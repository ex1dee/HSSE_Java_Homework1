package com.mipt.rezchikovsergey.sem2.spring_mvp.exceptions.storage;

import com.mipt.rezchikovsergey.sem2.spring_mvp.exceptions.base.StorageException;

public class ReadFileException extends StorageException {

  public ReadFileException(String filename) {
    this(filename, null);
  }

  public ReadFileException(String filename, Throwable cause) {
    super("Failed to read file " + filename, cause);
  }
}
