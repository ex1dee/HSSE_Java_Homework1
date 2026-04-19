package com.mipt.rezchikovsergey.sem2.spring_mvp.controller.task;

import com.mipt.rezchikovsergey.sem2.spring_mvp.common.model.dto.groups.OnCreate;
import com.mipt.rezchikovsergey.sem2.spring_mvp.common.model.dto.groups.OnUpdate;
import com.mipt.rezchikovsergey.sem2.spring_mvp.common.model.dto.request.TaskCreateDto;
import com.mipt.rezchikovsergey.sem2.spring_mvp.common.model.dto.request.TaskUpdateDto;
import com.mipt.rezchikovsergey.sem2.spring_mvp.common.model.dto.response.TaskResponseDto;
import com.mipt.rezchikovsergey.sem2.spring_mvp.service.GatewayTaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Tasks", description = "Management of tasks")
public class GatewayTaskController {
  private final GatewayTaskService taskService;

  @GetMapping
  @Operation(
      summary = "Get all tasks",
      description = "Returns all tasks with 'X-Total-Count' header.")
  @ApiResponse(responseCode = "200", description = "List of tasks retrieved")
  public ResponseEntity<List<TaskResponseDto>> getAllTasks() {
    List<TaskResponseDto> tasks = taskService.getAllTasks();

    return ResponseEntity.ok().header("X-Total-Count", String.valueOf(tasks.size())).body(tasks);
  }

  @GetMapping("/{id}")
  @Operation(summary = "Get task by ID")
  @ApiResponse(responseCode = "200", description = "Task found")
  @ApiResponse(responseCode = "404", description = "Task not found")
  public ResponseEntity<TaskResponseDto> getTaskById(@PathVariable("id") UUID id) {
    return ResponseEntity.ok(taskService.getTaskById(id));
  }

  @PostMapping
  @Operation(summary = "Create task")
  @ApiResponse(responseCode = "201", description = "Task created")
  @ApiResponse(responseCode = "400", description = "Validation error (OnCreate)")
  public ResponseEntity<TaskResponseDto> createTask(
      @Validated(OnCreate.class) @RequestBody TaskCreateDto request) {
    TaskResponseDto responseDto = taskService.createTask(request);

    URI location =
        ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(responseDto.id())
            .toUri();

    return ResponseEntity.created(location).body(responseDto);
  }

  @PutMapping("/{id}")
  @Operation(
      summary = "Update task",
      description = "Updates task. Throws error if dueDate is before creation.")
  @ApiResponse(responseCode = "204", description = "Task updated")
  @ApiResponse(responseCode = "400", description = "Invalid dates or validation error")
  @ApiResponse(responseCode = "404", description = "Task not found")
  public ResponseEntity<String> updateTask(
      @PathVariable("id") UUID id, @Validated(OnUpdate.class) @RequestBody TaskUpdateDto request) {
    taskService.updateTask(id, request);

    return ResponseEntity.noContent().build();
  }

  @DeleteMapping("/{id}")
  @Operation(summary = "Delete task")
  @ApiResponse(responseCode = "204", description = "Task deleted")
  @ApiResponse(responseCode = "404", description = "Task not found")
  public ResponseEntity<String> removeTask(@PathVariable("id") UUID id) {
    taskService.removeTask(id);

    return ResponseEntity.noContent().build();
  }
}
