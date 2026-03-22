package com.mipt.rezchikovsergey.sem2.spring_mvp.exceptions.task;

import com.mipt.rezchikovsergey.sem2.spring_mvp.exceptions.base.BadRequestException;

public class UnknownViewModeException extends BadRequestException {
  public UnknownViewModeException(String mode) {
    super("Mode `" + mode + "` is not supported");
  }
}
