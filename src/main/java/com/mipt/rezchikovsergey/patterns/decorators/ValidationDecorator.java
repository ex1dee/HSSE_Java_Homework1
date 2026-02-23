package com.mipt.rezchikovsergey.patterns.decorators;

import java.util.Objects;
import java.util.Optional;

public class ValidationDecorator extends DataServiceDecorator {
  public ValidationDecorator(DataService decoratedComponent) {
    super(decoratedComponent);
  }

  @Override
  public Optional<String> findDataByKey(String key) {
    Objects.requireNonNull(key, "Key cannot be null");

    return decoratedComponent.findDataByKey(key);
  }

  @Override
  public void saveData(String key, String data) {
    Objects.requireNonNull(key, "Key cannot be null");
    Objects.requireNonNull(data, "Data cannot be null");

    decoratedComponent.saveData(key, data);
  }

  @Override
  public boolean deleteData(String key) {
    Objects.requireNonNull(key, "Key cannot be null");

    return decoratedComponent.deleteData(key);
  }
}
