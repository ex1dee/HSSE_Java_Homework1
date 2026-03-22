package com.mipt.rezchikovsergey.sem2.spring_mvp.repository.impl;

import com.mipt.rezchikovsergey.sem2.spring_mvp.model.entity.Task;
import com.mipt.rezchikovsergey.sem2.spring_mvp.model.enums.TaskPriority;
import com.mipt.rezchikovsergey.sem2.spring_mvp.repository.TaskRepository;
import jakarta.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

@Repository
@Primary
public class InMemoryTaskRepository extends InMemoryRepository<Task> implements TaskRepository {
  @PostConstruct
  public void initTasks() {
    UUID firstId = UUID.randomUUID();
    save(
        Task.builder()
            .id(firstId)
            .title("Some Task 1")
            .description("Some Description 1")
            .dueDate(LocalDate.now().plusDays(1))
            .priority(TaskPriority.LOW)
            .tags(Set.of("task1"))
            .build());

    UUID secondId = UUID.randomUUID();
    save(
        Task.builder()
            .id(secondId)
            .title("Some Task 2")
            .description("Some Description 2")
            .dueDate(LocalDate.now().plusDays(3))
            .priority(TaskPriority.MEDIUM)
            .tags(Set.of("task2"))
            .build());
  }
}
