package com.mipt.rezchikovsergey.sem1.annotations_reflections;

import java.util.ArrayList;
import java.util.List;

public class ValidationResult {
  private boolean isValid = true;
  private List<String> errors = new ArrayList<>();

  public boolean isValid() {
    return isValid;
  }

  public List<String> getErrors() {
    return errors;
  }

  public void addError(String message) {
    if (message != null) errors.add(message);

    isValid = false;
  }
}
