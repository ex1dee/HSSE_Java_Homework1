package com.mipt.rezchikovsergey.sem2.spring_mvp.controller;

import com.mipt.rezchikovsergey.sem2.spring_mvp.model.dto.response.TaskResponseDto;
import com.mipt.rezchikovsergey.sem2.spring_mvp.model.entity.Task;
import com.mipt.rezchikovsergey.sem2.spring_mvp.model.mapper.TaskMapper;
import com.mipt.rezchikovsergey.sem2.spring_mvp.service.FavoritesService;
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
  public ResponseEntity<Void> addToFavorites(@PathVariable UUID taskId, HttpSession session) {
    favoritesService.addToFavorites(session, taskId);
    return ResponseEntity.ok().build();
  }

  @DeleteMapping("/{taskId}")
  public ResponseEntity<Void> remove(@PathVariable UUID taskId, HttpSession session) {
    favoritesService.removeFromFavorites(session, taskId);
    return ResponseEntity.noContent().build();
  }

  @GetMapping
  public ResponseEntity<List<TaskResponseDto>> getFavorites(HttpSession session) {
    List<Task> favoriteTasks = favoritesService.getFavoriteTasks(session);
    List<TaskResponseDto> responseDtos =
        favoriteTasks.stream().map(taskMapper::toResponseDto).toList();

    return ResponseEntity.ok(responseDtos);
  }
}
