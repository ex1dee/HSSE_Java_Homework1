package com.mipt.rezchikovsergey.sem1.decorators;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class CachingDecorator extends DataServiceDecorator {
  private Map<String, String> cache = new HashMap<>();

  public CachingDecorator(DataService decoratedComponent) {
    super(decoratedComponent);
  }

  @Override
  public Optional<String> findDataByKey(String key) {
    if (cache.containsKey(key)) return Optional.of(cache.get(key));

    Optional<String> result = decoratedComponent.findDataByKey(key);
    result.ifPresent(data -> cache.put(key, data));

    return result;
  }

  @Override
  public void saveData(String key, String data) {
    if (cache.containsKey(key) && cache.get(key).equals(data)) return;

    cache.put(key, data);
    decoratedComponent.saveData(key, data);
  }

  @Override
  public boolean deleteData(String key) {
    if (cache.containsKey(key)) {
      cache.remove(key);

      return decoratedComponent.deleteData(key);
    }

    return false;
  }
}
