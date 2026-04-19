package com.mipt.rezchikovsergey.sem2.spring_mvp.common.exception.task;

import com.mipt.rezchikovsergey.sem2.spring_mvp.common.validation.messages.ValidationMessages;
import com.mipt.rezchikovsergey.sem2.spring_mvp.common.exception.base.BadRequestException;

public class BadDateException extends BadRequestException {
  public BadDateException() {
    super(ValidationMessages.DUE_DATE_BEFORE_UPDATE);
  }
}
