package com.mipt.rezchikovsergey.patterns.decorators;

import java.util.Optional;

public class LoggingDecorator extends DataServiceDecorator {
  public LoggingDecorator(DataService decoratedComponent) {
    super(decoratedComponent);
  }

  @Override
  public Optional<String> findDataByKey(String key) {
    Optional<String> result = decoratedComponent.findDataByKey(key);

    result.ifPresentOrElse(
        value -> System.out.println("Found - key: " + key + ", data: " + value),
        () -> System.out.println("Not found - key: " + key));

    return result;
  }

  @Override
  public void saveData(String key, String data) {
    decoratedComponent.saveData(key, data);

    System.out.println("Saved - key: " + key + ", data: " + data);
  }

  @Override
  public boolean deleteData(String key) {
    boolean deleted = decoratedComponent.deleteData(key);

    if (deleted) System.out.println("Deleted - key: " + key);
    else System.out.println("Nothing to delete for key: " + key);

    return deleted;
  }
}
