package com.mipt.rezchikovsergey.sem2.spring_mvp.model.entity;

import com.mipt.rezchikovsergey.sem2.spring_mvp.model.enums.TaskPriority;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/** Сущность, представляющая задачу. */
@Data
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class Task extends BaseEntity {
  private String title;
  private String description;
  private LocalDate dueDate;
  private TaskPriority priority;
  private LocalDateTime createdAt;
  @Builder.Default private Set<String> tags = Set.of();
  @Builder.Default private boolean completed = false;
}
