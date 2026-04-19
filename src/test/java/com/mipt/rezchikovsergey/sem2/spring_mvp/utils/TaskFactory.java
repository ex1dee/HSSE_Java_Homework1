package com.mipt.rezchikovsergey.sem2.spring_mvp.utils;

import com.mipt.rezchikovsergey.sem2.spring_mvp.common.model.dto.request.TaskCreateDto;
import com.mipt.rezchikovsergey.sem2.spring_mvp.common.model.dto.request.TaskUpdateDto;
import com.mipt.rezchikovsergey.sem2.spring_mvp.common.model.enums.TaskPriority;
import com.mipt.rezchikovsergey.sem2.spring_mvp.external.model.entity.Task;
import com.mipt.rezchikovsergey.sem2.spring_mvp.external.model.entity.TaskAttachment;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

public class TaskFactory {
  public static final UUID DEFAULT_ATTACHMENT_ID = UUID.randomUUID();
  public static final UUID DEFAULT_TASK_ID = UUID.randomUUID();

  public static TaskCreateDto taskCreateDto() {
    return TaskCreateDto.builder()
        .title("Create Title")
        .description("Create Description")
        .dueDate(LocalDate.now().plusDays(1))
        .priority(TaskPriority.MEDIUM)
        .tags(Set.of("tag1", "tag2"))
        .build();
  }

  public static TaskUpdateDto taskUpdateDto() {
    return TaskUpdateDto.builder()
        .title("Update Title")
        .description("Update Description")
        .dueDate(LocalDate.now().plusDays(3))
        .priority(TaskPriority.HIGH)
        .tags(Set.of("tag"))
        .build();
  }

  public static Task task(UUID id) {
    return Task.builder()
        .id(id)
        .title("Test Task")
        .description("Test Description")
        .createdAt(LocalDateTime.now())
        .updatedAt(LocalDateTime.now())
        .dueDate(LocalDate.now().plusDays(1))
        .priority(TaskPriority.MEDIUM)
        .completed(false)
        .tags(Set.of("tag"))
        .build();
  }

  public static TaskAttachment attachment(Task task, UUID id) {
    return TaskAttachment.builder()
        .id(id)
        .task(task)
        .filename("file name")
        .storedFilename(UUID.randomUUID().toString())
        .contentType("application/octet-stream")
        .size(1L)
        .createdAt(LocalDateTime.now())
        .updatedAt(LocalDateTime.now())
        .build();
  }
}
