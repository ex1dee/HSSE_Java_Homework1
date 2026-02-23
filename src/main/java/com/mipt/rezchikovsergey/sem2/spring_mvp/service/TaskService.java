package com.mipt.rezchikovsergey.sem2.spring_mvp.service;

import com.mipt.rezchikovsergey.sem2.spring_mvp.exceptions.TaskNotFoundException;
import com.mipt.rezchikovsergey.sem2.spring_mvp.model.dto.request.CreateTaskRequest;
import com.mipt.rezchikovsergey.sem2.spring_mvp.model.dto.request.UpdateTaskRequest;
import com.mipt.rezchikovsergey.sem2.spring_mvp.model.entity.TaskEntity;
import com.mipt.rezchikovsergey.sem2.spring_mvp.repository.TaskRepository;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public class TaskService {

  private static final Logger log = LoggerFactory.getLogger(TaskService.class);
  private final Map<UUID, TaskEntity> taskCache = new HashMap<>();
  private final TaskRepository taskRepository;

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

  public TaskEntity getTaskById(UUID id) {
    TaskEntity task = taskCache.get(id);
    if (task != null) return task;

    return taskRepository.findById(id).orElseThrow(() -> new TaskNotFoundException(id));
  }

  public List<TaskEntity> getAllTasks() {
    return taskRepository.findAll();
  }

  public UUID createTask(CreateTaskRequest request) {
    TaskEntity entity =
        new TaskEntity(UUID.randomUUID(), request.title(), request.description(), false);

    taskRepository.save(entity);

    return entity.getId();
  }

  public void updateTask(UUID id, UpdateTaskRequest request) {
    TaskEntity task = taskRepository.findById(id).orElseThrow(() -> new TaskNotFoundException(id));

    if (request.title() != null) {
      task.setTitle(request.title());
    }

    if (request.description() != null) {
      task.setDescription(request.description());
    }

    if (request.completed() != null) {
      task.setCompleted(request.completed());
    }

    taskRepository.save(task);
  }

  public void removeTask(UUID id) {
    if (taskRepository.findById(id).isEmpty()) throw new TaskNotFoundException(id);

    taskRepository.removeById(id);
  }
}
