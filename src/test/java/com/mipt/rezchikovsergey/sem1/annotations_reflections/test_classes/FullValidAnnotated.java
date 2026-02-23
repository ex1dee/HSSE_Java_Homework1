package com.mipt.rezchikovsergey.sem1.annotations_reflections.test_classes;

import com.mipt.rezchikovsergey.sem1.annotations_reflections.annotations.Email;
import com.mipt.rezchikovsergey.sem1.annotations_reflections.annotations.NotNull;
import com.mipt.rezchikovsergey.sem1.annotations_reflections.annotations.Range;
import com.mipt.rezchikovsergey.sem1.annotations_reflections.annotations.Size;

public class FullValidAnnotated {
  @NotNull
  @Size(min = 1, max = 30)
  @Range(min = 1, max = 2)
  @Email
  String email = "test@example.com";
}
