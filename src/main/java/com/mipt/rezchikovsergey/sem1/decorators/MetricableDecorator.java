package com.mipt.rezchikovsergey.sem1.decorators;

import java.time.Duration;
import java.util.Optional;

public class MetricableDecorator extends DataServiceDecorator {
  public MetricableDecorator(DataService decoratedComponent) {
    super(decoratedComponent);
  }

  @Override
  public Optional<String> findDataByKey(String key) {
    long startNanoTime = System.nanoTime();
    Optional<String> result = decoratedComponent.findDataByKey(key);
    long endNanoTime = System.nanoTime();

    new MetricService().sendMetric(Duration.ofNanos(endNanoTime - startNanoTime));

    return result;
  }

  @Override
  public void saveData(String key, String data) {
    long startNanoTime = System.nanoTime();
    decoratedComponent.saveData(key, data);
    long endNanoTime = System.nanoTime();

    new MetricService().sendMetric(Duration.ofNanos(endNanoTime - startNanoTime));
  }

  @Override
  public boolean deleteData(String key) {
    long startNanoTime = System.nanoTime();
    boolean result = decoratedComponent.deleteData(key);
    long endNanoTime = System.nanoTime();

    new MetricService().sendMetric(Duration.ofNanos(endNanoTime - startNanoTime));

    return result;
  }

  public static class MetricService {
    public void sendMetric(Duration duration) {
      System.out.println("Метод выполнялся: " + duration.toString());
    }
  }
}
