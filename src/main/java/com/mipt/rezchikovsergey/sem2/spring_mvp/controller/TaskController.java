package com.mipt.rezchikovsergey.sem2.spring_mvp.controller;

import com.mipt.rezchikovsergey.sem2.spring_mvp.model.dto.request.TaskCreateDto;
import com.mipt.rezchikovsergey.sem2.spring_mvp.model.dto.request.TaskUpdateDto;
import com.mipt.rezchikovsergey.sem2.spring_mvp.model.dto.response.IDResponseDto;
import com.mipt.rezchikovsergey.sem2.spring_mvp.model.dto.response.MessageResponseDto;
import com.mipt.rezchikovsergey.sem2.spring_mvp.model.entity.Task;
import com.mipt.rezchikovsergey.sem2.spring_mvp.service.TaskService;
import java.net.URI;
import java.util.List;
import java.util.UUID;
import org.springframework.http.ResponseEntity;
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
public class TaskController {
  private final TaskService taskService;

  public TaskController(TaskService taskService) {
    this.taskService = taskService;
  }

  @GetMapping
  public List<Task> getAllTasks() {
    return taskService.getAllTasks();
  }

  @GetMapping("/{id}")
  public Task getTaskById(@PathVariable("id") UUID id) {
    return taskService.getTaskById(id);
  }

  @PostMapping
  public ResponseEntity<IDResponseDto> createTask(@RequestBody TaskCreateDto request) {
    UUID id = taskService.createTask(request);
    URI location =
        ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(id).toUri();

    return ResponseEntity.created(location).body(new IDResponseDto(id));
  }

  @PutMapping("/{id}")
  public ResponseEntity<MessageResponseDto> updateTask(
      @PathVariable("id") UUID id, @RequestBody TaskUpdateDto request) {
    taskService.updateTask(id, request);

    return ResponseEntity.ok(new MessageResponseDto("Task was successfully updated"));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<MessageResponseDto> removeTask(@PathVariable("id") UUID id) {
    taskService.removeTask(id);

    return ResponseEntity.ok(new MessageResponseDto("Task was successfully removed"));
  }
}
