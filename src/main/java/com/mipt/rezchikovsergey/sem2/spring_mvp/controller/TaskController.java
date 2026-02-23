package com.mipt.rezchikovsergey.sem2.spring_mvp.controller;

import com.mipt.rezchikovsergey.sem2.spring_mvp.model.dto.request.CreateTaskRequest;
import com.mipt.rezchikovsergey.sem2.spring_mvp.model.dto.request.UpdateTaskRequest;
import com.mipt.rezchikovsergey.sem2.spring_mvp.model.dto.response.IDResponse;
import com.mipt.rezchikovsergey.sem2.spring_mvp.model.dto.response.MessageResponse;
import com.mipt.rezchikovsergey.sem2.spring_mvp.model.entity.TaskEntity;
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

@RestController
@RequestMapping("/api/tasks")
public class TaskController {
  private final TaskService taskService;

  public TaskController(TaskService taskService) {
    this.taskService = taskService;
  }

  @GetMapping
  public List<TaskEntity> getAllTasks() {
    return taskService.getAllTasks();
  }

  @GetMapping("/{id}")
  public TaskEntity getTaskById(@PathVariable("id") UUID id) {
    return taskService.getTaskById(id);
  }

  @PostMapping
  public ResponseEntity<IDResponse> createTask(@RequestBody CreateTaskRequest request) {
    UUID id = taskService.createTask(request);
    URI location =
        ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(id).toUri();

    return ResponseEntity.created(location).body(new IDResponse(id));
  }

  @PutMapping("/{id}")
  public ResponseEntity<MessageResponse> updateTask(
      @PathVariable("id") UUID id, @RequestBody UpdateTaskRequest request) {
    taskService.updateTask(id, request);

    return ResponseEntity.ok(new MessageResponse("Task was successfully updated"));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<MessageResponse> removeTask(@PathVariable("id") UUID id) {
    taskService.removeTask(id);

    return ResponseEntity.ok(new MessageResponse("Task was successfully removed"));
  }
}
