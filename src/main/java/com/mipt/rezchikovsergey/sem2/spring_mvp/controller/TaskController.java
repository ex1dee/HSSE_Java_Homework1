package com.mipt.rezchikovsergey.sem2.spring_mvp.controller;

import com.mipt.rezchikovsergey.sem2.spring_mvp.model.dto.groups.OnCreate;
import com.mipt.rezchikovsergey.sem2.spring_mvp.model.dto.groups.OnUpdate;
import com.mipt.rezchikovsergey.sem2.spring_mvp.model.dto.request.TaskCreateDto;
import com.mipt.rezchikovsergey.sem2.spring_mvp.model.dto.request.TaskUpdateDto;
import com.mipt.rezchikovsergey.sem2.spring_mvp.model.dto.response.TaskResponseDto;
import com.mipt.rezchikovsergey.sem2.spring_mvp.model.entity.Task;
import com.mipt.rezchikovsergey.sem2.spring_mvp.model.mapper.TaskMapper;
import com.mipt.rezchikovsergey.sem2.spring_mvp.service.TaskService;
import java.net.URI;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

/** REST-контроллер для управления задачами. Предоставляет API для CRUD операций над задачами. */
@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
public class TaskController {
  private final TaskService taskService;
  private final TaskMapper taskMapper;

  @GetMapping
  public ResponseEntity<List<TaskResponseDto>> getAllTasks() {
    List<Task> tasks = taskService.getAllTasks();
    List<TaskResponseDto> responseDtos = tasks.stream().map(taskMapper::toResponseDto).toList();

    return ResponseEntity.ok()
        .header("X-Total-Count", String.valueOf(tasks.size()))
        .body(responseDtos);
  }

  @GetMapping("/{id}")
  public ResponseEntity<TaskResponseDto> getTaskById(@PathVariable("id") UUID id) {
    Task task = taskService.getTaskById(id);
    TaskResponseDto responseDto = taskMapper.toResponseDto(task);

    return ResponseEntity.ok(responseDto);
  }

  @PostMapping
  public ResponseEntity<TaskResponseDto> createTask(
      @Validated(OnCreate.class) @RequestBody TaskCreateDto request) {
    Task task = taskService.createTask(request);
    URI location =
        ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(task.getId())
            .toUri();

    TaskResponseDto responseDto = taskMapper.toResponseDto(task);
    return ResponseEntity.created(location).body(responseDto);
  }

  @PutMapping("/{id}")
  public ResponseEntity<String> updateTask(
      @PathVariable("id") UUID id, @Validated(OnUpdate.class) @RequestBody TaskUpdateDto request) {
    taskService.updateTask(id, request);

    return ResponseEntity.ok().body("Task was successfully updated");
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<String> removeTask(@PathVariable("id") UUID id) {
    taskService.removeTask(id);

    return ResponseEntity.ok().body("Task was successfully removed");
  }
}
