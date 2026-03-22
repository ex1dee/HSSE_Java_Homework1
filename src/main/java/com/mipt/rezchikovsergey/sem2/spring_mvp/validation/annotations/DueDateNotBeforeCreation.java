package com.mipt.rezchikovsergey.sem2.spring_mvp.validation.annotations;

import com.mipt.rezchikovsergey.sem2.spring_mvp.validation.messages.ValidationMessages;
import com.mipt.rezchikovsergey.sem2.spring_mvp.validation.validators.DueDateNotBeforeCreationValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Constraint(validatedBy = DueDateNotBeforeCreationValidator.class)
public @interface DueDateNotBeforeCreation {
  String message() default ValidationMessages.DUE_DATE_BEFORE_UPDATE;

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
