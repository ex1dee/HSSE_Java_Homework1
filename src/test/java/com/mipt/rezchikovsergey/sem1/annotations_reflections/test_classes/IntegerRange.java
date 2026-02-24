package com.mipt.rezchikovsergey.sem1.annotations_reflections.test_classes;

import com.mipt.rezchikovsergey.sem1.annotations_reflections.annotations.Range;

public class IntegerRange {
  @Range(min = 3, max = 5)
  int number;

  public enum TestInteger {
    EXACT_MIN(3),
    EXACT_MAX(5),
    MIDDLE(4),
    TOO_LOW(2),
    TOO_HIGH(6);

    private final int integer;

    TestInteger(int integer) {
      this.integer = integer;
    }
  }

  public IntegerRange(TestInteger testInteger) {
    this.number = testInteger.integer;
  }
}
