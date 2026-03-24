package com.mipt.rezchikovsergey.sem2.spring_mvp.controller;

import com.mipt.rezchikovsergey.sem2.spring_mvp.model.dto.response.TaskResponseDto;
import com.mipt.rezchikovsergey.sem2.spring_mvp.model.entity.Task;
import com.mipt.rezchikovsergey.sem2.spring_mvp.model.mapper.TaskMapper;
import com.mipt.rezchikovsergey.sem2.spring_mvp.service.FavoritesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/favorites")
@RequiredArgsConstructor
public class FavoritesController {
  private final FavoritesService favoritesService;
  private final TaskMapper taskMapper;

  @PostMapping("/{taskId}")
  @Operation(summary = "Add to favorites")
  @ApiResponse(responseCode = "200", description = "Added")
  @ApiResponse(responseCode = "404", description = "Task doesn't exist")
  public ResponseEntity<Void> addToFavorites(@PathVariable UUID taskId, HttpSession session) {
    favoritesService.addToFavorites(session, taskId);
    return ResponseEntity.ok().build();
  }

  @DeleteMapping("/{taskId}")
  @Operation(summary = "Remove from favorites")
  @ApiResponse(responseCode = "204", description = "Removed")
  public ResponseEntity<Void> remove(@PathVariable UUID taskId, HttpSession session) {
    favoritesService.removeFromFavorites(session, taskId);
    return ResponseEntity.noContent().build();
  }

  @GetMapping
  @Operation(
      summary = "Get favorite tasks",
      description =
          "Retrieves all task objects that were previously marked as favorites in the current HTTP session.")
  @ApiResponse(
      responseCode = "200",
      description = "List of favorite tasks retrieved from session and database")
  public ResponseEntity<List<TaskResponseDto>> getFavorites(HttpSession session) {
    List<Task> favoriteTasks = favoritesService.getFavoriteTasks(session);
    List<TaskResponseDto> responseDtos =
        favoriteTasks.stream().map(taskMapper::toResponseDto).toList();

    return ResponseEntity.ok(responseDtos);
  }
}
