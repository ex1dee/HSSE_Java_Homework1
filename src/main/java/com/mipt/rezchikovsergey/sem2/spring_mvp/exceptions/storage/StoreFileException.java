package com.mipt.rezchikovsergey.sem2.spring_mvp.exceptions.storage;

import com.mipt.rezchikovsergey.sem2.spring_mvp.exceptions.base.StorageException;

public class StoreFileException extends StorageException {
  public StoreFileException(String filename, Throwable cause) {
    super("Failed to store file " + filename, cause);
  }
}
