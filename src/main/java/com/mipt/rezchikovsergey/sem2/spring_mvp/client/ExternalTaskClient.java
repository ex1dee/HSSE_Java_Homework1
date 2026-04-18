package com.mipt.rezchikovsergey.sem2.spring_mvp.client;

import com.mipt.rezchikovsergey.sem2.spring_mvp.common.model.dto.request.TaskCreateDto;
import com.mipt.rezchikovsergey.sem2.spring_mvp.common.model.dto.request.TaskUpdateDto;
import com.mipt.rezchikovsergey.sem2.spring_mvp.common.model.dto.response.TaskResponseDto;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
@RequiredArgsConstructor
public class ExternalTaskClient {
  private final RestClient externalClient;

  public TaskResponseDto createTask(TaskCreateDto request) {
    ResponseEntity<TaskResponseDto> response =
        externalClient
            .post()
            .uri("/external/v1/tasks")
            .contentType(MediaType.APPLICATION_JSON)
            .body(request)
            .retrieve()
            .toEntity(TaskResponseDto.class);

    return response.getBody();
  }

  public void updateTask(UUID id, TaskUpdateDto request) {
    externalClient
        .put()
        .uri("/external/v1/tasks/{id}", id)
        .contentType(MediaType.APPLICATION_JSON)
        .body(request)
        .retrieve()
        .toBodilessEntity();
  }

  public TaskResponseDto getTaskById(UUID id) {
    return externalClient
        .get()
        .uri("/external/v1/tasks/{id}", id)
        .retrieve()
        .body(TaskResponseDto.class);
  }

  public List<TaskResponseDto> getAllTasks() {
    return externalClient
        .get()
        .uri("/external/v1/tasks")
        .retrieve()
        .body(new ParameterizedTypeReference<List<TaskResponseDto>>() {});
  }

  public void removeTask(UUID id) {
    externalClient.delete().uri("/external/v1/tasks/{id}", id).retrieve().toBodilessEntity();
  }
}
