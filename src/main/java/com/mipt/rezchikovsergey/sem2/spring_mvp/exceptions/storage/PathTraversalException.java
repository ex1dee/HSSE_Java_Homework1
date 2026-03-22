package com.mipt.rezchikovsergey.sem2.spring_mvp.exceptions.storage;

import com.mipt.rezchikovsergey.sem2.spring_mvp.exceptions.base.StorageException;

public class PathTraversalException extends StorageException {
  public PathTraversalException() {
    super("Internal path points outside storage");
  }
}
