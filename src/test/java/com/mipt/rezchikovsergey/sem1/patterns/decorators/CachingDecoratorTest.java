package com.mipt.rezchikovsergey.sem1.patterns.decorators;

import static com.mipt.rezchikovsergey.sem1.patterns.decorators.DecoratorsTestUtils.createMockDataService;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;

class CachingDecoratorTest {
  @Test
  public void find_existingKey() {
    DataService mockService = createMockDataService("key", "value");

    CachingDecorator cachingDecorator = new CachingDecorator(mockService);
    cachingDecorator.findDataByKey("key");
    cachingDecorator.findDataByKey("key");

    verify(mockService, times(1)).findDataByKey("key");
  }

  @Test
  public void find_notExistingKey() {
    DataService mockService = createMockDataService("key1", "value");

    CachingDecorator cachingDecorator = new CachingDecorator(mockService);
    cachingDecorator.findDataByKey("key2");

    verify(mockService, times(0)).findDataByKey("key1");
  }

  @Test
  public void save_sameValue() {
    DataService mockService = createMockDataService("key", "value");

    CachingDecorator cachingDecorator = new CachingDecorator(mockService);
    cachingDecorator.saveData("key", "value");
    cachingDecorator.saveData("key", "value");

    verify(mockService, times(1)).saveData("key", "value");
  }

  @Test
  public void save_differentValue() {
    DataService mockService = createMockDataService("key", "value1");

    CachingDecorator cachingDecorator = new CachingDecorator(mockService);
    cachingDecorator.saveData("key", "value1");
    cachingDecorator.saveData("key", "value2");

    verify(mockService, times(1)).saveData("key", "value2");
  }

  @Test
  public void save_nonExistingKey() {
    DataService mockService = createMockDataService("key1", "value");

    CachingDecorator cachingDecorator = new CachingDecorator(mockService);
    cachingDecorator.saveData("key1", "value");
    cachingDecorator.saveData("key2", "value");

    verify(mockService, times(1)).saveData("key2", "value");
  }

  @Test
  public void delete_existingKey() {
    DataService mockService = createMockDataService("key", "value");

    CachingDecorator cachingDecorator = new CachingDecorator(mockService);
    cachingDecorator.saveData("key", "value");
    cachingDecorator.deleteData("key");

    verify(mockService, times(1)).deleteData("key");
  }

  @Test
  public void delete_nonExistingKey() {
    DataService mockService = createMockDataService("key", "value");

    CachingDecorator cachingDecorator = new CachingDecorator(mockService);
    cachingDecorator.saveData("key", "value");

    verify(mockService, times(0)).deleteData("key");
  }

  @Test
  public void logging_find_existingKey() {
    DataService mockService = createMockDataService("key", "value");

    LoggingDecorator loggingDecorator = new LoggingDecorator(mockService);
  }
}
