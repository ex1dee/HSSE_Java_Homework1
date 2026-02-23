package com.mipt.rezchikovsergey.sem2.spring_mvp.repository;

import com.mipt.rezchikovsergey.sem2.spring_mvp.model.entity.Task;
import jakarta.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class InMemoryTaskRepository implements TaskRepository {
  private final Map<UUID, Task> tasks = new HashMap<>();

  @PostConstruct
  public void initTasks() {
    UUID id1 = UUID.randomUUID();
    tasks.put(id1, new Task(id1, "Some Task 1", "Some Description 1", false));

    UUID id2 = UUID.randomUUID();
    tasks.put(id2, new Task(id2, "Some Task 2", "Some Description 2", false));
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
