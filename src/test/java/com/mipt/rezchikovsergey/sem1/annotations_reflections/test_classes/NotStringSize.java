package com.mipt.rezchikovsergey.sem1.annotations_reflections.test_classes;

import com.mipt.rezchikovsergey.sem1.annotations_reflections.annotations.Size;

public class NotStringSize {
  @Size(min = 1, max = 2)
  int notString = 0;
}
