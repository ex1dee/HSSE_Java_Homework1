package com.mipt.rezchikovsergey.sem2.spring_mvp.service;

import static com.mipt.rezchikovsergey.sem2.spring_mvp.constants.ResilienceConstants.EXTERNAL_API_INSTANCE;

import com.mipt.rezchikovsergey.sem2.spring_mvp.client.ExternalTaskClient;
import com.mipt.rezchikovsergey.sem2.spring_mvp.common.model.dto.request.TaskCreateDto;
import com.mipt.rezchikovsergey.sem2.spring_mvp.common.model.dto.request.TaskUpdateDto;
import com.mipt.rezchikovsergey.sem2.spring_mvp.common.model.dto.response.TaskResponseDto;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GatewayTaskService {
  private final ExternalTaskClient client;

  @RateLimiter(name = EXTERNAL_API_INSTANCE)
  @CircuitBreaker(name = EXTERNAL_API_INSTANCE, fallbackMethod = "fallbackGetTasks")
  public List<TaskResponseDto> getAllTasks() {
    return client.getAllTasks();
  }

  @RateLimiter(name = EXTERNAL_API_INSTANCE)
  @CircuitBreaker(name = EXTERNAL_API_INSTANCE)
  public TaskResponseDto getTaskById(UUID id) {
    return client.getTaskById(id);
  }

  @RateLimiter(name = EXTERNAL_API_INSTANCE)
  @CircuitBreaker(name = EXTERNAL_API_INSTANCE)
  public TaskResponseDto createTask(TaskCreateDto request) {
    return client.createTask(request);
  }

  @RateLimiter(name = EXTERNAL_API_INSTANCE)
  @CircuitBreaker(name = EXTERNAL_API_INSTANCE)
  public void updateTask(UUID id, TaskUpdateDto request) {
    client.updateTask(id, request);
  }

  @RateLimiter(name = EXTERNAL_API_INSTANCE)
  @CircuitBreaker(name = EXTERNAL_API_INSTANCE)
  public void removeTask(UUID id) {
    client.removeTask(id);
  }

  private List<TaskResponseDto> fallbackGetTasks(Throwable t) {
    return Collections.emptyList();
  }
}
