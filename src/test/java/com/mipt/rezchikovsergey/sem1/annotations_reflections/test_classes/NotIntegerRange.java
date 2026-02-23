package com.mipt.rezchikovsergey.sem1.annotations_reflections.test_classes;

import com.mipt.rezchikovsergey.sem1.annotations_reflections.annotations.Range;

public class NotIntegerRange {
  @Range(min = 1, max = 2)
  String notInt = "0";
}
