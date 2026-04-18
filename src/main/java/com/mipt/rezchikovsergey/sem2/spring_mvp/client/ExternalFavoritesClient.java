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

  public void addToFavorites(UUID taskId) {
    externalClient
        .post()
        .uri("/external/v1/favorites/{taskId}", taskId)
        .retrieve()
        .toBodilessEntity();
  }

  public void removeFromFavorites(UUID taskId) {
    externalClient
        .delete()
        .uri("/external/v1/favorites/{taskId}", taskId)
        .retrieve()
        .toBodilessEntity();
  }

  public List<TaskResponseDto> getFavoriteTasks() {
    return externalClient
        .get()
        .uri("/external/v1/favorites")
        .retrieve()
        .body(new ParameterizedTypeReference<List<TaskResponseDto>>() {});
  }
}
