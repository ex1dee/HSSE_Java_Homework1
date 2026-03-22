package com.mipt.rezchikovsergey.sem2.spring_mvp.model.entity;

import com.mipt.rezchikovsergey.sem2.spring_mvp.model.enums.TaskPriority;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/** Сущность, представляющая задачу. */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Task {
  private UUID id;
  private String title;
  private String description;
  private LocalDateTime createdAt;
  private LocalDate dueDate;
  private TaskPriority priority;
  private Set<String> tags;
  private boolean completed;
}
