package com.mipt.rezchikovsergey.sem1.annotations_reflections.test_classes;

import com.mipt.rezchikovsergey.sem1.annotations_reflections.annotations.Size;

public class StringSize {
  @Size(min = 3, max = 5)
  String string;

  public enum TestString {
    EXACT_MIN("123"),
    EXACT_MAX("12345"),
    MIDDLE("1234"),
    EMPTY(""),
    TOO_SHORT("12"),
    TOO_LONG("123456");

    private final String string;

    TestString(String string) {
      this.string = string;
    }
  }

  public StringSize(TestString testString) {
    this.string = testString.string;
  }
}
