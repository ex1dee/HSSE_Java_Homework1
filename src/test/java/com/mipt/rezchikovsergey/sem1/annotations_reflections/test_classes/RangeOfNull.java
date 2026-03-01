package com.mipt.rezchikovsergey.sem1.annotations_reflections.test_classes;

import com.mipt.rezchikovsergey.sem1.annotations_reflections.annotations.Range;

public class RangeOfNull {
  @Range(min = 1, max = 2)
  Integer nullInt = null;
}
