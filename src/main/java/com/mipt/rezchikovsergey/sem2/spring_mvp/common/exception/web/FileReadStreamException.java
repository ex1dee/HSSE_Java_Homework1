package com.mipt.rezchikovsergey.sem2.spring_mvp.common.exception.web;

import com.mipt.rezchikovsergey.sem2.spring_mvp.common.exception.base.BaseProjectException;

public class FileReadStreamException extends BaseProjectException {
  public FileReadStreamException(String filename, Throwable cause) {
    super("Failed to open input stream for file: " + filename, cause);
  }
}
