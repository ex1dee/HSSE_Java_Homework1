package com.mipt.rezchikovsergey.sem2.spring_mvp.external.controller;

import com.mipt.rezchikovsergey.sem2.spring_mvp.common.model.dto.request.TaskCreateDto;
import com.mipt.rezchikovsergey.sem2.spring_mvp.common.model.dto.request.TaskUpdateDto;
import com.mipt.rezchikovsergey.sem2.spring_mvp.common.model.dto.response.TaskResponseDto;
import com.mipt.rezchikovsergey.sem2.spring_mvp.external.model.entity.Task;
import com.mipt.rezchikovsergey.sem2.spring_mvp.external.model.mapper.TaskMapper;
import com.mipt.rezchikovsergey.sem2.spring_mvp.external.service.TaskService;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/external/v1/tasks")
@RequiredArgsConstructor
public class ExternalTaskController {
  private final TaskService taskService;
  private final TaskMapper taskMapper;

  @GetMapping
  public List<TaskResponseDto> getTasks() {
    List<Task> tasks = taskService.getAllTasks();

    return tasks.stream().map(taskMapper::toResponseDto).toList();
  }

  @GetMapping("/{id}")
  public TaskResponseDto getTaskById(@PathVariable UUID id) {
    return taskMapper.toResponseDto(taskService.getTaskById(id));
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public TaskResponseDto createTask(@RequestBody TaskCreateDto request) {
    Task task = taskService.createTask(request);
    return taskMapper.toResponseDto(task);
  }

  @PutMapping("/{id}")
  public void updateTask(@PathVariable UUID id, @RequestBody TaskUpdateDto request) {
    taskService.updateTask(id, request);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteTask(@PathVariable UUID id) {
    taskService.removeTask(id);
  }
}
