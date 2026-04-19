package com.mipt.rezchikovsergey.sem2.spring_mvp.external.controller;

import com.mipt.rezchikovsergey.sem2.spring_mvp.common.model.dto.response.TaskResponseDto;
import com.mipt.rezchikovsergey.sem2.spring_mvp.external.model.entity.Task;
import com.mipt.rezchikovsergey.sem2.spring_mvp.external.model.mapper.TaskMapper;
import com.mipt.rezchikovsergey.sem2.spring_mvp.external.service.FavoritesService;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/external/v1/favorites")
@RequiredArgsConstructor
public class ExternalFavoritesController {
  private final FavoritesService favoritesService;
  private final TaskMapper taskMapper;

  @PostMapping("/{taskId}")
  public ResponseEntity<Void> addToFavorites(
      @RequestHeader("X-User-Id") UUID userId, @PathVariable UUID taskId) {
    favoritesService.addToFavorites(userId, taskId);
    return ResponseEntity.ok().build();
  }

  @DeleteMapping("/{taskId}")
  public ResponseEntity<Void> removeFromFavorites(
      @RequestHeader("X-User-Id") UUID userId, @PathVariable UUID taskId) {
    favoritesService.removeFromFavorites(userId, taskId);
    return ResponseEntity.noContent().build();
  }

  @GetMapping
  public ResponseEntity<List<TaskResponseDto>> getFavorites(
      @RequestHeader("X-User-Id") UUID userId) {
    List<Task> favoriteTasks = favoritesService.getFavoriteTasks(userId);
    List<TaskResponseDto> responseDtos =
        favoriteTasks.stream().map(taskMapper::toResponseDto).toList();

    return ResponseEntity.ok(responseDtos);
  }
}
