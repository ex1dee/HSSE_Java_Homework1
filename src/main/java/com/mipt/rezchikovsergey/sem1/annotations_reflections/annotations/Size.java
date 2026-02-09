package com.mipt.rezchikovsergey.annotations_reflections.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Size {
  int min();

  int max();

  String message() default "String must be between {min} and {max} characters";
}
