package com.mipt.rezchikovsergey.sem2.spring_mvp.exceptions.storage;

import com.mipt.rezchikovsergey.sem2.spring_mvp.exceptions.base.StorageException;

public class DeleteFileException extends StorageException {
  public DeleteFileException(String filename, Throwable cause) {
    super("Failed to delete file " + filename, cause);
  }
}
