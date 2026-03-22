package com.mipt.rezchikovsergey.sem2.spring_mvp.exceptions;

import com.mipt.rezchikovsergey.sem2.spring_mvp.exceptions.base.BadRequestException;
import com.mipt.rezchikovsergey.sem2.spring_mvp.validation.messages.ValidationMessages;

public class BadDateException extends BadRequestException {
  public BadDateException() {
    super(ValidationMessages.DUE_DATE_BEFORE_UPDATE);
  }
}
