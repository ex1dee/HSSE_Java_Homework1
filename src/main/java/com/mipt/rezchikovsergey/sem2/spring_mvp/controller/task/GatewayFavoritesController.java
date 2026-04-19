package com.mipt.rezchikovsergey.sem2.spring_mvp.controller.task;

import com.mipt.rezchikovsergey.sem2.spring_mvp.common.model.dto.response.TaskResponseDto;
import com.mipt.rezchikovsergey.sem2.spring_mvp.security.AppUserDetails;
import com.mipt.rezchikovsergey.sem2.spring_mvp.service.GatewayFavoritesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/favorites")
@RequiredArgsConstructor
public class GatewayFavoritesController {
  private final GatewayFavoritesService favoritesService;

  @PostMapping("/{taskId}")
  @Operation(summary = "Add to favorites")
  @ApiResponse(responseCode = "200", description = "Added")
  @ApiResponse(responseCode = "404", description = "Task doesn't exist")
  public ResponseEntity<Void> addToFavorites(
      @AuthenticationPrincipal AppUserDetails userDetails, @PathVariable UUID taskId) {
    favoritesService.addToFavorites(userDetails.getUserId(), taskId);
    return ResponseEntity.ok().build();
  }

  @DeleteMapping("/{taskId}")
  @Operation(summary = "Remove from favorites")
  @ApiResponse(responseCode = "204", description = "Removed")
  public ResponseEntity<Void> remove(
      @AuthenticationPrincipal AppUserDetails userDetails, @PathVariable UUID taskId) {
    favoritesService.removeFromFavorites(userDetails.getUserId(), taskId);
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
  public ResponseEntity<List<TaskResponseDto>> getFavorites(
      @AuthenticationPrincipal AppUserDetails userDetails) {
    return ResponseEntity.ok(favoritesService.getFavoriteTasks(userDetails.getUserId()));
  }
}
