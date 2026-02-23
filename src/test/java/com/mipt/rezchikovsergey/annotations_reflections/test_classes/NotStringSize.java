package com.mipt.rezchikovsergey.annotations_reflections.test_classes;

import com.mipt.rezchikovsergey.annotations_reflections.annotations.Size;

public class NotStringSize {
  @Size(min = 1, max = 2) int notString = 0;
}
