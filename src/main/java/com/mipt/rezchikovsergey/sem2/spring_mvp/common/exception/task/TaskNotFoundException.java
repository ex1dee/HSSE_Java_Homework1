package com.mipt.rezchikovsergey.sem2.spring_mvp.common.exception.task;

import com.mipt.rezchikovsergey.sem2.spring_mvp.common.exception.base.NotFoundException;
import java.util.UUID;

public class TaskNotFoundException extends NotFoundException {
  public TaskNotFoundException(String message) {
    super(message);
  }

  public TaskNotFoundException(UUID id) {
    super("Task with id " + id + " not found");
  }
}
