package com.mipt.rezchikovsergey.patterns.decorators;

import static com.mipt.rezchikovsergey.patterns.decorators.DecoratorsTestUtils.captureConsoleOutput;
import static com.mipt.rezchikovsergey.patterns.decorators.DecoratorsTestUtils.createMockDataService;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.sql.SQLOutput;
import java.util.Optional;
import org.junit.jupiter.api.Test;

class LoggingDecoratorTest {
  @Test
  public void find_existingKey() {
    DataService mockService = createMockDataService("key", "value");

    LoggingDecorator loggingDecorator = new LoggingDecorator(mockService);
    String output = captureConsoleOutput(() -> {
      loggingDecorator.findDataByKey("key");
    });

    assertEquals("Found - key: key, data: value", output);
  }

  @Test
  public void find_nonExistingKey() {
    DataService mockService = createMockDataService("key1", "value");

    LoggingDecorator loggingDecorator = new LoggingDecorator(mockService);
    String output = captureConsoleOutput(() -> {
      loggingDecorator.findDataByKey("key2");
    });

    assertEquals("Not found - key: key2", output);
  }

  @Test
  public void save() {
    DataService mockService = createMockDataService("key", "value");

    LoggingDecorator loggingDecorator = new LoggingDecorator(mockService);
    String output = captureConsoleOutput(() -> {
      loggingDecorator.saveData("key", "value");
    });

    assertEquals("Saved - key: key, data: value", output);
  }

  @Test
  public void delete_existingKey() {
    DataService mockService = createMockDataService("key", "value");
    when(mockService.deleteData("key")).thenReturn(true);

    LoggingDecorator loggingDecorator = new LoggingDecorator(mockService);
    String output = captureConsoleOutput(() -> {
      loggingDecorator.deleteData("key");
    });

    assertEquals("Deleted - key: key", output);
  }

  @Test
  public void delete_nonExistingKey() {
    DataService mockService = createMockDataService("key1", "value");
    when(mockService.deleteData("key1")).thenReturn(true);

    LoggingDecorator loggingDecorator = new LoggingDecorator(mockService);
    String output = captureConsoleOutput(() -> {
      loggingDecorator.deleteData("key2");
    });

    assertEquals("Nothing to delete for key: key2", output);
  }
}
