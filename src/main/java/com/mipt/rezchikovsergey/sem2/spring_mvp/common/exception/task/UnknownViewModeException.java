package com.mipt.rezchikovsergey.sem2.spring_mvp.common.exception.task;

import com.mipt.rezchikovsergey.sem2.spring_mvp.common.exception.base.BadRequestException;

public class UnknownViewModeException extends BadRequestException {
  public UnknownViewModeException(String mode) {
    super("Mode `" + mode + "` is not supported");
  }
}
