package com.mipt.rezchikovsergey.sem2.spring_mvp.storage;

import java.io.InputStream;
import java.nio.file.Path;
import org.springframework.core.io.Resource;

public interface FileStorage {
  void store(InputStream inputStream, Path directory, String filename);

  Resource loadAsResource(Path directory, String filename);

  void delete(Path directory, String filename);
}
