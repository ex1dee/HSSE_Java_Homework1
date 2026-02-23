package com.mipt.rezchikovsergey.sem2.spring_mvp.repository;

import com.mipt.rezchikovsergey.sem2.spring_mvp.model.entity.Task;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Repository;

@Repository
public class StubTaskRepository implements TaskRepository {
  private final Task stubTask;

  public StubTaskRepository() {
    stubTask = new Task(UUID.randomUUID(), "Stub Task", "amamamam", true);
  }

  @Override
  public void save(Task task) {
    System.out.println("Task saved");
  }

  @Override
  public List<Task> findAll() {
    return List.of(stubTask);
  }

  @Override
  public Optional<Task> findById(UUID id) {
    return Optional.of(
        new Task(id, stubTask.getTitle(), stubTask.getDescription(), stubTask.isCompleted()));
  }

  @Override
  public void removeById(UUID id) {
    System.out.println("Task removed");
  }
}
