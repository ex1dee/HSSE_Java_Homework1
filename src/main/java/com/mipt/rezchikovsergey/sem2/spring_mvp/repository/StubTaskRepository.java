package com.mipt.rezchikovsergey.sem2.spring_mvp.repository;

import com.mipt.rezchikovsergey.sem2.spring_mvp.Task;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Repository;

@Repository
public class StubTaskRepository implements TaskRepository {
  private final Task stubTask;

  public StubTaskRepository() {
    stubTask = new Task();
    stubTask.setTitle("Stub Task");
    stubTask.setDescription("amamamam");
    stubTask.setCompleted(true);
  }

  @Override
  public void save(Task task) {
    System.out.println("Task saved");
  }

  @Override
  public Optional<Task> findById(UUID id) {
    stubTask.setId(id);

    return Optional.of(stubTask);
  }

  @Override
  public void removeById(UUID id) {
    System.out.println("Task removed");
  }
}
