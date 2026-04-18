package com.mipt.rezchikovsergey.sem2.spring_mvp.common.exception.storage;

public class DeleteFileException extends StorageException {
  public DeleteFileException(String filename, Throwable cause) {
    super("Failed to delete file " + filename, cause);
  }
}
