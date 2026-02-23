package com.mipt.rezchikovsergey.io;

import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import org.junit.jupiter.api.Test;

class TextFileAnalyzerTest {
  @Test
  public void shouldAnalyzeFile() throws IOException {
    TextFileAnalyzer.AnalysisResult result = analyzeTestFile();

    assertEquals(2, result.lineCount());
    assertEquals(4, result.wordCount());
    assertEquals(15, result.charCount());
  }

  @Test
  public void shouldSaveAnalysisResult() throws IOException {
    TextFileAnalyzer.AnalysisResult result = analyzeTestFile();

    Path outputFilePath = Files.createTempFile("save_analysis_test", ".txt");
    TextFileAnalyzer.saveAnalysisResult(result, outputFilePath.toString());

    assertTrue(Files.exists(outputFilePath));

    try (BufferedReader reader = new BufferedReader(new FileReader(outputFilePath.toString()))) {
      String line = reader.readLine();

      assertNotNull(line);
      assertEquals(result.toString(), line);
      assertNull(reader.readLine());
    }
  }

  private TextFileAnalyzer.AnalysisResult analyzeTestFile() throws IOException {
    Path testFilePath = Files.createTempFile("analyze_test", ".txt");
    Files.write(testFilePath, Arrays.asList("12345 6789", "ABC DEF"));

    return TextFileAnalyzer.analyzeFile(testFilePath.toString());
  }
}
