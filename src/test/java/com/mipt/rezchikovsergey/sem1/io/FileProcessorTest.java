package com.mipt.rezchikovsergey.sem1.io;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class FileProcessorTest {
  @Test
  void shouldSplitFile() throws IOException {
    Path testFilePath = createTestRandomFile("split_file_test", ".txt", (byte) 1, 1500);
    Path outputDirPath = Files.createTempDirectory("split_dir_test");
    List<Path> parts =
        FileProcessor.splitFile(testFilePath.toString(), outputDirPath.toString(), 500);

    assertEquals(3, parts.size());

    for (int i = 0; i < parts.size(); i++) {
      assertTrue(Files.exists(parts.get(i)));
      assertTrue(parts.get(i).toString().endsWith(".part" + (i + 1)));
      assertEquals(500, Files.size(parts.get(i)));
      ;
    }
  }

  @Test
  public void shouldMergeFile() throws IOException {
    List<Path> parts =
        Arrays.asList(
            createTestRandomFile("merge_file_test.txt", ".part2", (byte) 2, 500),
            createTestRandomFile("merge_file_test.txt", ".part1", (byte) 1, 500),
            createTestRandomFile("merge_file_test.txt", ".part3", (byte) 3, 300));

    Path outputPath = Files.createTempFile("merged_file_test", ".txt");
    FileProcessor.mergeFile(parts, outputPath.toString());
    byte[] mergedData = Files.readAllBytes(outputPath);

    assertTrue(Files.exists(outputPath));
    assertEquals(1300, Files.size(outputPath));
    assertEquals(1, mergedData[0]);
    assertEquals(2, mergedData[500]);
    assertEquals(3, mergedData[1000]);
  }

  private Path createTestRandomFile(String prefix, String suffix, byte fillValue, int size)
      throws IOException {
    Path testFilePath = Files.createTempFile(prefix, suffix);
    byte[] testData = new byte[size];
    Arrays.fill(testData, fillValue);
    Files.write(testFilePath, testData);

    return testFilePath;
  }
}
