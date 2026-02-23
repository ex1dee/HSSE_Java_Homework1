package com.mipt.rezchikovsergey.sem2.spring_mvp.repository;

import com.mipt.rezchikovsergey.sem2.spring_mvp.model.entity.TaskEntity;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Repository;

@Repository
public class StubTaskRepository implements TaskRepository {
  private final TaskEntity stubTask;

  public StubTaskRepository() {
    stubTask = new TaskEntity(UUID.randomUUID(), "Stub Task", "amamamam", true);
  }

  @Override
  public void save(TaskEntity task) {
    System.out.println("Task saved");
  }

  @Override
  public List<TaskEntity> findAll() {
    return List.of(stubTask);
  }

  @Override
  public Optional<TaskEntity> findById(UUID id) {
    return Optional.of(
        new TaskEntity(id, stubTask.getTitle(), stubTask.getDescription(), stubTask.isCompleted()));
  }

  @Override
  public void removeById(UUID id) {
    System.out.println("Task removed");
  }
}
