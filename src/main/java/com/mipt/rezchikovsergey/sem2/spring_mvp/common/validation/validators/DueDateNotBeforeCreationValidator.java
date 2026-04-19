package com.mipt.rezchikovsergey.sem2.spring_mvp.common.validation.validators;

import com.mipt.rezchikovsergey.sem2.spring_mvp.common.validation.annotations.DueDateNotBeforeCreation;
import com.mipt.rezchikovsergey.sem2.spring_mvp.common.model.dto.request.TaskCreateDto;
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
