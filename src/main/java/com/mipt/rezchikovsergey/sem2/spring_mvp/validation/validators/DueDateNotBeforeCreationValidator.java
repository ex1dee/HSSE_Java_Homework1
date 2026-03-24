package com.mipt.rezchikovsergey.sem2.spring_mvp.validation.validators;

import com.mipt.rezchikovsergey.sem2.spring_mvp.model.dto.request.TaskCreateDto;
import com.mipt.rezchikovsergey.sem2.spring_mvp.validation.annotations.DueDateNotBeforeCreation;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.time.LocalDate;

public class DueDateNotBeforeCreationValidator
    implements ConstraintValidator<DueDateNotBeforeCreation, TaskCreateDto> {

  @Override
  public boolean isValid(TaskCreateDto dto, ConstraintValidatorContext context) {
    if (dto == null || dto.dueDate() == null) {
      return true;
    }

    LocalDate createdAt = LocalDate.now();

    return !dto.dueDate().isBefore(createdAt);
  }
}
