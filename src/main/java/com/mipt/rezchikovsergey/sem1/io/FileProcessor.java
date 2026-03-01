package com.mipt.rezchikovsergey.sem1.io;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

public class FileProcessor {
  public static List<Path> splitFile(String sourcePathStr, String outputDir, int partSize)
      throws IOException {
    Path sourcePath = Path.of(sourcePathStr);

    if (!Files.exists(sourcePath))
      throw new FileNotFoundException("File not found: " + sourcePathStr);

    List<Path> partPaths = new ArrayList<>();

    try (FileChannel readChannel = FileChannel.open(sourcePath, StandardOpenOption.READ)) {
      ByteBuffer buffer = ByteBuffer.allocate(partSize);
      int currentPart = 1;

      while (readChannel.read(buffer) > 0) {
        Path outputPath = Paths.get(outputDir, sourcePath.getFileName() + ".part" + currentPart);
        buffer.flip();

        try (FileChannel writeChannel =
            FileChannel.open(outputPath, StandardOpenOption.WRITE, StandardOpenOption.CREATE)) {
          writeChannel.write(buffer);
        }

        partPaths.add(outputPath);
        buffer.clear();
        currentPart++;
      }
    }

    return partPaths;
  }

  public static void mergeFile(List<Path> partPaths, String outputPathStr) throws IOException {
    List<Path> sortedPaths = getSortedParts(partPaths);

    try (FileChannel writeChannel =
        FileChannel.open(
            Path.of(outputPathStr), StandardOpenOption.WRITE, StandardOpenOption.CREATE)) {
      for (Path partPath : sortedPaths) {
        if (!Files.exists(partPath)) throw new FileNotFoundException("Part not found: " + partPath);

        try (FileChannel readChannel = FileChannel.open(partPath, StandardOpenOption.READ)) {
          ByteBuffer buffer = ByteBuffer.allocate(8 * 1024);

          while (readChannel.read(buffer) > 0) {
            buffer.flip();
            writeChannel.write(buffer);
            buffer.clear();
          }
        }
      }
    }
  }

  private static List<Path> getSortedParts(List<Path> partPaths) {
    return partPaths.stream()
        .sorted(
            (p1, p2) -> {
              int part1;
              int part2;

              try {
                part1 = extractPartNumber(p1);
                part2 = extractPartNumber(p2);
              } catch (IllegalArgumentException e) {
                throw new RuntimeException("Sorting by part number failed", e);
              }

              return Integer.compare(part1, part2);
            })
        .toList();
  }

  private static int extractPartNumber(Path path) throws IllegalArgumentException {
    if (!path.toString().matches(".*\\.part\\d+"))
      throw new IllegalArgumentException("Invalid file part path: " + path);

    String[] pathParts = path.toString().split("part");
    return Integer.parseInt(pathParts[pathParts.length - 1]);
  }
}
