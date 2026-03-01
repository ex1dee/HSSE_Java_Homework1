package com.mipt.rezchikovsergey.sem1.annotations_reflections;

import com.mipt.rezchikovsergey.sem1.annotations_reflections.annotations.Email;
import com.mipt.rezchikovsergey.sem1.annotations_reflections.annotations.NotNull;
import com.mipt.rezchikovsergey.sem1.annotations_reflections.annotations.Range;
import com.mipt.rezchikovsergey.sem1.annotations_reflections.annotations.Size;
import java.lang.reflect.Field;

public class Validator {
  public static ValidationResult validate(Object object) {
    ValidationResult validationResult = new ValidationResult();

    for (Field field : object.getClass().getDeclaredFields()) {
      field.setAccessible(true);

      if (field.isAnnotationPresent(NotNull.class)) {
        checkNotNull(object, field, validationResult);
      }

      if (field.isAnnotationPresent(Size.class)) {
        checkSize(object, field, validationResult);
      }

      if (field.isAnnotationPresent(Range.class)) {
        checkRange(object, field, validationResult);
      }

      if (field.isAnnotationPresent(Email.class)) {
        checkEmail(object, field, validationResult);
      }
    }

    return validationResult;
  }

  private static void checkNotNull(Object object, Field field, ValidationResult validationResult) {
    NotNull annotation = field.getAnnotation(NotNull.class);

    if (getFieldValue(object, field) == null) {
      validationResult.addError(annotation.message());
    }
  }

  private static void checkSize(Object object, Field field, ValidationResult validationResult) {
    Size annotation = field.getAnnotation(Size.class);
    Object fieldValue = getFieldValue(object, field);

    if (fieldValue instanceof String) {
      int size = ((String) fieldValue).length();

      if (size > annotation.max() || size < annotation.min()) {
        validationResult.addError(
            annotation
                .message()
                .replace("{min}", String.valueOf(annotation.min()))
                .replace("{max}", String.valueOf(annotation.max())));
      }
    }
  }

  private static void checkRange(Object object, Field field, ValidationResult validationResult) {
    Range annotation = field.getAnnotation(Range.class);
    Object fieldValue = getFieldValue(object, field);

    if (fieldValue instanceof Integer) {
      int number = (int) fieldValue;

      if (number > annotation.max() || number < annotation.min()) {
        validationResult.addError(
            annotation
                .message()
                .replace("{min}", String.valueOf(annotation.min()))
                .replace("{max}", String.valueOf(annotation.max())));
      }
    }
  }

  private static void checkEmail(Object object, Field field, ValidationResult validationResult) {
    Email annotation = field.getAnnotation(Email.class);
    Object fieldValue = getFieldValue(object, field);

    if (fieldValue instanceof String) {
      if (!((String) fieldValue).matches("[A-Za-z._+0-9]+@[a-z]+\\.[a-z]+")) {
        validationResult.addError(annotation.message());
      }
    }
  }

  private static Object getFieldValue(Object object, Field field) {
    try {
      return field.get(object);
    } catch (IllegalAccessException e) {
      throw new ValidationException(
          "Cannot access field " + field.getName() + " of " + object.getClass().getName(), e);
    }
  }
}
