package com.mipt.rezchikovsergey.sem2.spring_mvp.repository;

import com.mipt.rezchikovsergey.sem2.spring_mvp.model.entity.Task;
import com.mipt.rezchikovsergey.sem2.spring_mvp.model.enums.TaskPriority;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** Репозиторий-заглушка для работы с задачами. */
public class StubTaskRepository implements TaskRepository {

  private static final Logger log = LoggerFactory.getLogger(StubTaskRepository.class);
  private final Task stubTask;

  public StubTaskRepository() {
    stubTask =
        Task.builder()
            .id(UUID.randomUUID())
            .title("Stub Task")
            .description("amamamam")
            .createdAt(LocalDateTime.now())
            .dueDate(LocalDate.now().plusDays(1))
            .priority(TaskPriority.LOW)
            .tags(Set.of("work", "coding"))
            .completed(false)
            .build();
  }

  @Override
  public void save(Task task) {
    log.info("Task saved");
  }

  @Override
  public List<Task> findAll() {
    return List.of(stubTask);
  }

  @Override
  public Optional<Task> findById(UUID id) {
    return Optional.of(stubTask.toBuilder().id(id).build());
  }

  @Override
  public void removeById(UUID id) {
    log.info("Task removed");
  }
}
