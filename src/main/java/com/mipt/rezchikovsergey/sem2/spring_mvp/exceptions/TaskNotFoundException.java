package com.mipt.rezchikovsergey.sem2.spring_mvp.exceptions;

import com.mipt.rezchikovsergey.sem2.spring_mvp.exceptions.base.NotFoundException;
import java.util.UUID;

public class TaskNotFoundException extends NotFoundException {
  public TaskNotFoundException(UUID id) {
    super("Task with id " + id + " not found");
  }
}
