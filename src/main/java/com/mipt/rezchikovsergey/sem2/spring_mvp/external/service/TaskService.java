package com.mipt.rezchikovsergey.sem2.spring_mvp.external.service;

import com.mipt.rezchikovsergey.sem2.spring_mvp.common.exception.task.BadDateException;
import com.mipt.rezchikovsergey.sem2.spring_mvp.common.exception.task.TaskNotFoundException;
import com.mipt.rezchikovsergey.sem2.spring_mvp.common.model.dto.request.TaskCreateDto;
import com.mipt.rezchikovsergey.sem2.spring_mvp.common.model.dto.request.TaskUpdateDto;
import com.mipt.rezchikovsergey.sem2.spring_mvp.external.model.entity.Task;
import com.mipt.rezchikovsergey.sem2.spring_mvp.external.model.mapper.TaskMapper;
import com.mipt.rezchikovsergey.sem2.spring_mvp.external.repository.TaskRepository;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Сервис для управления бизнес-логикой задач. Осуществляет взаимодействие между контроллером и
 * репозиториями, а также управляет кэшированием задач.
 */
@Service
@Primary
@RequiredArgsConstructor
public class TaskService {

  private final AttachmentService attachmentService;
  private final TaskRepository taskRepository;
  private final TaskMapper taskMapper;

  @Transactional(readOnly = true)
  public List<Task> getAllTasks() {
    return taskRepository.findAll();
  }

  @Transactional
  public Task createTask(TaskCreateDto request) {
    Task task = taskMapper.toEntity(request);
    taskRepository.save(task);

    return task;
  }

  @Transactional
  public void updateTask(UUID id, TaskUpdateDto request) {
    Task task = getTaskById(id);

    if (request.dueDate() != null
        && request.dueDate().isBefore(task.getCreatedAt().toLocalDate())) {
      throw new BadDateException();
    }

    taskMapper.updateEntity(request, task);
    taskRepository.save(task);
  }

  @Transactional
  public void removeTask(UUID id) {
    Task task = getTaskById(id);

    attachmentService.deleteAllAttachments(task);
    taskRepository.removeById(id);
  }

  @Transactional
  public void bulkCompleteTasks(List<UUID> ids) {
    for (UUID id : ids) {
      Task task = getTaskById(id);
      task.setCompleted(true);
    }
  }

  @Transactional(readOnly = true)
  public List<Task> findAllWithAttachments() {
    return taskRepository.findAllWithAttachments();
  }

  @Transactional(readOnly = true)
  public Task getTaskById(UUID id) {
    return taskRepository.findById(id).orElseThrow(() -> new TaskNotFoundException(id));
  }
}
