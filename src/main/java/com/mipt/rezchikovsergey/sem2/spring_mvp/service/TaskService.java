package com.mipt.rezchikovsergey.sem2.spring_mvp.service;

import com.mipt.rezchikovsergey.sem2.spring_mvp.Task;
import com.mipt.rezchikovsergey.sem2.spring_mvp.repository.TaskRepository;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public class TaskService {

  private static final Logger log = LoggerFactory.getLogger(TaskService.class);
  private final TaskRepository taskRepository;

  private final Map<UUID, Task> taskCache = new HashMap<>();

  public TaskService(TaskRepository taskRepository) {
    this.taskRepository = taskRepository;
  }

  @PostConstruct
  private void initCache() {
    taskRepository.findAll().forEach(task -> taskCache.put(task.getId(), task));
  }

  @PreDestroy
  private void logCache() {
    log.info("Num of tasks in cache before destroying TaskService: {}", taskCache.size());
  }

  public Optional<Task> getTaskById(UUID id) {
    return Optional.ofNullable(taskCache.get(id)).or(() -> taskRepository.findById(id));
  }
}
