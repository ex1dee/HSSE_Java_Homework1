package com.mipt.rezchikovsergey.sem2.spring_mvp.repository;

import com.mipt.rezchikovsergey.sem2.spring_mvp.Task;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class InMemoryTaskRepository implements TaskRepository {
  private final Map<UUID, Task> tasks = new HashMap<>();

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
  public Optional<Task> findById(UUID id) {
    return Optional.ofNullable(tasks.get(id));
  }

  @Override
  public void removeById(UUID id) {
    tasks.remove(id);
  }
}
