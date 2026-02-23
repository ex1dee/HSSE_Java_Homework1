package com.mipt.rezchikovsergey.sem2.spring_mvp.exceptions;

import java.util.UUID;

public class TaskNotFoundException extends RuntimeException {
  public TaskNotFoundException(UUID id) {
    super("Task with id " + id + " not found");
  }
}
