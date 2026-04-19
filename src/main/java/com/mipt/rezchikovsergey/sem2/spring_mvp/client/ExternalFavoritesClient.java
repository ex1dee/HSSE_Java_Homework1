package com.mipt.rezchikovsergey.sem2.spring_mvp.client;

import com.mipt.rezchikovsergey.sem2.spring_mvp.common.model.dto.response.TaskResponseDto;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
@RequiredArgsConstructor
public class ExternalFavoritesClient {
  private final RestClient externalClient;

  public void addToFavorites(UUID userId, UUID taskId) {
    externalClient
        .post()
        .uri("/external/v1/favorites/{taskId}", taskId)
        .header("X-User-Id", userId.toString())
        .retrieve()
        .toBodilessEntity();
  }

  public void removeFromFavorites(UUID userId, UUID taskId) {
    externalClient
        .delete()
        .uri("/external/v1/favorites/{taskId}", taskId)
        .header("X-User-Id", userId.toString())
        .retrieve()
        .toBodilessEntity();
  }

  public List<TaskResponseDto> getFavoriteTasks(UUID userId) {
    return externalClient
        .get()
        .uri("/external/v1/favorites")
        .header("X-User-Id", userId.toString())
        .retrieve()
        .body(new ParameterizedTypeReference<List<TaskResponseDto>>() {});
  }
}
