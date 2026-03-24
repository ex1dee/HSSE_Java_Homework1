package com.mipt.rezchikovsergey.sem2.spring_mvp.exceptions.task;

import com.mipt.rezchikovsergey.sem2.spring_mvp.exceptions.base.NotFoundException;
import java.util.UUID;

public class TaskAttachmentNotFoundException extends NotFoundException {
  public TaskAttachmentNotFoundException(UUID id) {
    super("Task attachment with id " + id + " not found");
  }
}
