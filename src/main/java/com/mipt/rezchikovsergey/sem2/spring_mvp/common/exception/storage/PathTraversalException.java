package com.mipt.rezchikovsergey.sem2.spring_mvp.common.exception.storage;

public class PathTraversalException extends StorageException {
  public PathTraversalException() {
    super("Internal path points outside storage");
  }
}
