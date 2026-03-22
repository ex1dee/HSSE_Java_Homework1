package com.mipt.rezchikovsergey.sem2.spring_mvp.repository;

import com.mipt.rezchikovsergey.sem2.spring_mvp.model.entity.Task;
import com.mipt.rezchikovsergey.sem2.spring_mvp.model.enums.TaskPriority;
import jakarta.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

@Repository
@Primary
public class InMemoryTaskRepository implements TaskRepository {
  private final ConcurrentMap<UUID, Task> tasks = new ConcurrentHashMap<>();

  @PostConstruct
  public void initTasks() {
    UUID firstId = UUID.randomUUID();
    tasks.put(
        firstId,
        Task.builder()
            .id(firstId)
            .title("Some Task 1")
            .description("Some Description 1")
            .dueDate(LocalDate.now().plusDays(1))
            .priority(TaskPriority.LOW)
            .tags(Set.of("task1"))
            .build());

    UUID secondId = UUID.randomUUID();
    tasks.put(
        secondId,
        Task.builder()
            .id(firstId)
            .title("Some Task 2")
            .description("Some Description 2")
            .dueDate(LocalDate.now().plusDays(3))
            .priority(TaskPriority.MEDIUM)
            .tags(Set.of("task2"))
            .build());
  }

  @Override
  public void save(Task task) {
    if (task == null) {
      throw new IllegalArgumentException("Task cannot be null");
    }

    if (task.getId() == null) {
      task.setId(UUID.randomUUID());
    }

    tasks.put(task.getId(), task);
  }

  @Override
  public List<Task> findAll() {
    return tasks.values().stream().toList();
  }

  @Override
  public Optional<Task> findById(UUID id) {
    return Optional.ofNullable(tasks.get(id));
  }

  @Override
  public void removeById(UUID id) {
    tasks.remove(id);
  }
}
