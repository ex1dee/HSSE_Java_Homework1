package com.mipt.rezchikovsergey.patterns.decorators;

import static com.mipt.rezchikovsergey.patterns.decorators.DecoratorsTestUtils.createMockDataService;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class ValidationDecoratorTest {
  @Test
  public void find_keyIsNull() {
    ValidationDecorator validationDecorator = createValidationDecorator();
    assertThrows(NullPointerException.class, () -> validationDecorator.findDataByKey(null));
  }

  @Test
  public void find_keyIsNotNull() {
    ValidationDecorator validationDecorator = createValidationDecorator();
    validationDecorator.findDataByKey("");
  }

  @Test
  public void save_keyIsNull() {
    ValidationDecorator validationDecorator = createValidationDecorator();
    assertThrows(NullPointerException.class, () -> validationDecorator.saveData(null, ""));
  }

  @Test
  public void save_DataIsNull() {
    ValidationDecorator validationDecorator = createValidationDecorator();
    assertThrows(NullPointerException.class, () -> validationDecorator.saveData("", null));
  }

  @Test
  public void save_keyAndDataAreNull() {
    ValidationDecorator validationDecorator = createValidationDecorator();
    assertThrows(NullPointerException.class, () -> validationDecorator.saveData(null, null));
  }

  @Test
  public void save_keyAndDataIsNotNull() {
    ValidationDecorator validationDecorator = createValidationDecorator();

    validationDecorator.saveData("", "");
  }

  @Test
  public void delete_keyIsNull() {
    ValidationDecorator validationDecorator = createValidationDecorator();
    assertThrows(NullPointerException.class, () -> validationDecorator.deleteData(null));
  }

  @Test
  public void delete_keyIsNotNull() {
    ValidationDecorator validationDecorator = createValidationDecorator();
    validationDecorator.deleteData("");
  }

  private ValidationDecorator createValidationDecorator() {
    DataService mockService = createMockDataService("key", "value");

    return new ValidationDecorator(mockService);
  }
}
