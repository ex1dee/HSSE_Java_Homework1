package com.mipt.rezchikovsergey.sem2.spring_mvp.storage;

import com.mipt.rezchikovsergey.sem2.spring_mvp.exceptions.storage.DeleteFileException;
import com.mipt.rezchikovsergey.sem2.spring_mvp.exceptions.storage.PathTraversalException;
import com.mipt.rezchikovsergey.sem2.spring_mvp.exceptions.storage.ReadFileException;
import com.mipt.rezchikovsergey.sem2.spring_mvp.exceptions.storage.StoreFileException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;

@Service
public class FileSystemStorage implements FileStorage {
  @Override
  public void store(InputStream inputStream, Path directory, String filename) {
    try {
      Files.createDirectories(directory);
      Path filePath = safeResolveFilePath(directory, filename);
      Files.copy(inputStream, filePath);
    } catch (IOException e) {
      throw new StoreFileException(filename, e);
    }
  }

  @Override
  public Resource loadAsResource(Path directory, String filename) {
    Path filePath = safeResolveFilePath(directory, filename);
    Resource resource;

    try {
      resource = new UrlResource(filePath.toUri());
    } catch (MalformedURLException e) {
      throw new ReadFileException(filename, e);
    }

    if (!resource.exists() || !resource.isReadable()) {
      throw new ReadFileException(filename);
    }

    return resource;
  }

  @Override
  public void delete(Path directory, String filename) {
    Path filePath = safeResolveFilePath(directory, filename);

    try {
      Files.deleteIfExists(filePath);
    } catch (IOException e) {
      throw new DeleteFileException(filename, e);
    }
  }

  private Path safeResolveFilePath(Path directory, String filename) {
    Path absoluteDirectory = directory.toAbsolutePath();
    Path filePath = absoluteDirectory.resolve(filename).normalize().toAbsolutePath();

    if (!filePath.startsWith(directory)) {
      throw new PathTraversalException();
    }

    return filePath;
  }
}
