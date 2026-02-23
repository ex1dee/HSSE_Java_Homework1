package com.mipt.rezchikovsergey.sem1.annotations_reflections.test_classes;

import com.mipt.rezchikovsergey.annotations_reflections.annotations.Size;

public class SizeOfNull {
  @Size(min = 1, max = 2)
  String nullString = null;
}
