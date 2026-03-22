package com.mipt.rezchikovsergey.sem2.spring_mvp.service;

import com.mipt.rezchikovsergey.sem2.spring_mvp.exceptions.TaskNotFoundException;
import com.mipt.rezchikovsergey.sem2.spring_mvp.model.dto.request.TaskCreateDto;
import com.mipt.rezchikovsergey.sem2.spring_mvp.model.dto.request.TaskUpdateDto;
import com.mipt.rezchikovsergey.sem2.spring_mvp.model.entity.Task;
import com.mipt.rezchikovsergey.sem2.spring_mvp.model.mapper.TaskMapper;
import com.mipt.rezchikovsergey.sem2.spring_mvp.repository.TaskRepository;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

/**
 * Сервис для управления бизнес-логикой задач. Осуществляет взаимодействие между контроллером и
 * репозиториями, а также управляет кэшированием задач.
 */
@Service
@Primary
@RequiredArgsConstructor
public class TaskService {

  private static final Logger log = LoggerFactory.getLogger(TaskService.class);
  private final ConcurrentMap<UUID, Task> taskCache = new ConcurrentHashMap<>();
  private final TaskRepository taskRepository;
  private final TaskMapper taskMapper;

  @PostConstruct
  private void init() {
    taskRepository.findAll().forEach(task -> taskCache.put(task.getId(), task));
  }

  @PreDestroy
  private void logCache() {
    log.info("Num of tasks in cache before destroying TaskService: {}", taskCache.size());
  }

  public Task getTaskById(UUID id) {
    Task task = taskCache.get(id);
    if (task != null) return task;

    return taskRepository.findById(id).orElseThrow(() -> new TaskNotFoundException(id));
  }

  public List<Task> getAllTasks() {
    return taskRepository.findAll();
  }

  public UUID createTask(TaskCreateDto request) {
    Task task = taskMapper.toEntity(request);

    taskRepository.save(task);
    taskCache.put(task.getId(), task);

    return task.getId();
  }

  public void updateTask(UUID id, TaskUpdateDto request) {
    Task task = taskRepository.findById(id).orElseThrow(() -> new TaskNotFoundException(id));

    taskMapper.updateEntity(request, task);
    taskRepository.save(task);
    taskCache.put(task.getId(), task);
  }

  public void removeTask(UUID id) {
    if (taskRepository.findById(id).isEmpty()) throw new TaskNotFoundException(id);

    taskRepository.removeById(id);
  }
}
