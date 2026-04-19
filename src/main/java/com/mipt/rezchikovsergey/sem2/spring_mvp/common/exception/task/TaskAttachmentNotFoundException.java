package com.mipt.rezchikovsergey.sem2.spring_mvp.common.exception.task;

import com.mipt.rezchikovsergey.sem2.spring_mvp.common.exception.base.NotFoundException;
import java.util.UUID;

public class TaskAttachmentNotFoundException extends NotFoundException {
  public TaskAttachmentNotFoundException(UUID id) {
    super("Task attachment with id " + id + " not found");
  }
}
