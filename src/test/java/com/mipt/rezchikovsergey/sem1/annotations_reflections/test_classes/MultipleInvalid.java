package com.mipt.rezchikovsergey.sem1.annotations_reflections.test_classes;

import com.mipt.rezchikovsergey.annotations_reflections.annotations.Email;
import com.mipt.rezchikovsergey.annotations_reflections.annotations.NotNull;
import com.mipt.rezchikovsergey.annotations_reflections.annotations.Range;
import com.mipt.rezchikovsergey.annotations_reflections.annotations.Size;

public class MultipleInvalid {
  @NotNull Object nullObject = null;

  @Size(min = 2, max = 10)
  String tooShort = "1";

  @Range(min = 1, max = 10)
  int tooLow = -1;

  @Email String incorrectEmail = "test@example";
}
