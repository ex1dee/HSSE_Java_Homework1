package com.mipt.rezchikovsergey.patterns.decorators;

import org.junit.jupiter.api.Test;

import static com.mipt.rezchikovsergey.patterns.decorators.DecoratorsTestUtils.captureConsoleOutput;
import static com.mipt.rezchikovsergey.patterns.decorators.DecoratorsTestUtils.createMockDataService;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MetricableDecoratorTest {
  @Test
  public void find() {
    DataService mockService = createMockDataService("key", "value");

    MetricableDecorator metricableDecorator = new MetricableDecorator(mockService);
    shouldSendMetric(() -> {
      metricableDecorator.findDataByKey("key");
    });
  }

  @Test
  public void save() {
    DataService mockService = createMockDataService("key", "value");

    MetricableDecorator metricableDecorator = new MetricableDecorator(mockService);
    shouldSendMetric(() -> {
      metricableDecorator.saveData("key", "value");
    });
  }

  @Test
  public void delete() {
    DataService mockService = createMockDataService("key", "value");

    MetricableDecorator metricableDecorator = new MetricableDecorator(mockService);
    shouldSendMetric(() -> {
      metricableDecorator.deleteData("key");
    });
  }

  private static void shouldSendMetric(Runnable action) {
    String output = captureConsoleOutput(action);

    assertTrue(output.contains("Метод выполнялся: PT"));
    assertTrue(output.contains("S"));
  }
}
