package com.mipt.rezchikovsergey.annotations_reflections.test_classes;

import com.mipt.rezchikovsergey.annotations_reflections.annotations.Range;
import com.mipt.rezchikovsergey.annotations_reflections.annotations.Size;

public class RangeOfNull {
  @Range(min = 1, max = 2) Integer nullInt = null;
}
