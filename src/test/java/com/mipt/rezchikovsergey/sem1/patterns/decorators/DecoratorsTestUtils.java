package com.mipt.rezchikovsergey.sem1.patterns.decorators;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.mipt.rezchikovsergey.sem1.decorators.DataService;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Optional;

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
