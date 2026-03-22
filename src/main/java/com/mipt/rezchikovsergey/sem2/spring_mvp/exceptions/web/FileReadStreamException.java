package com.mipt.rezchikovsergey.sem2.spring_mvp.exceptions.web;

import com.mipt.rezchikovsergey.sem2.spring_mvp.exceptions.base.BaseProjectException;

public class FileReadStreamException extends BaseProjectException {
  public FileReadStreamException(String filename, Throwable cause) {
    super("Failed to open input stream for file: " + filename, cause);
  }
}
