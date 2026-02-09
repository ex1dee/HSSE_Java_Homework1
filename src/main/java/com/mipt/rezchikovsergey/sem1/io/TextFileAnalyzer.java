package com.mipt.rezchikovsergey.io;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

public class TextFileAnalyzer {
  public record AnalysisResult(long lineCount, long wordCount, long charCount) {
    @Override
    public String toString() {
      return "AnalysisResult{"
          + "lineCount="
          + lineCount
          + ", wordCount="
          + wordCount
          + ", charCount="
          + charCount
          + '}';
    }
  }

  public static AnalysisResult analyzeFile(String filePathStr) throws IOException {
    Path filePath = Path.of(filePathStr);

    if (!Files.exists(filePath)) throw new FileNotFoundException("File not found: " + filePathStr);

    int lineCount = 0;
    int wordCount = 0;
    int charCount = 0;

    try (BufferedReader reader = Files.newBufferedReader(filePath)) {
      String line;

      while ((line = reader.readLine()) != null) {
        String[] words = line.trim().split("\\s+");

        for (String word : words) {
          charCount += word.length();
        }

        wordCount += words.length;
        lineCount += 1;
      }
    }

    return new AnalysisResult(lineCount, wordCount, charCount);
  }

  public static void saveAnalysisResult(AnalysisResult result, String outputPathStr)
      throws IOException {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputPathStr))) {
      writer.write(result.toString());
    }
  }
}
