package com.mipt.rezchikovsergey.sem2.spring_mvp.common.model.enums;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Task priority levels")
public enum TaskPriority {
  @Schema(description = "Low priority task")
  LOW,

  @Schema(description = "Default priority task")
  MEDIUM,

  @Schema(description = "High priority task")
  HIGH
}
