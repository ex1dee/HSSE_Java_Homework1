package com.mipt.rezchikovsergey.patterns.decorators;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Optional;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class DecoratorsTestUtils {
  public static String captureConsoleOutput(Runnable action) {
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    PrintStream originalOut = System.out;

    System.setOut(new PrintStream(outputStream));
    action.run();
    System.setOut(originalOut);

    return outputStream.toString().trim();
  }

  public static DataService createMockDataService(String key, String value) {
    DataService mockService = mock(DataService.class);
    when(mockService.findDataByKey(key)).thenReturn(Optional.ofNullable(value));

    return mockService;
  }
}
