package com.mipt.rezchikovsergey.sem2.spring_mvp.service;

import static com.mipt.rezchikovsergey.sem2.spring_mvp.constants.ResilienceConstants.EXTERNAL_API_INSTANCE;

import com.mipt.rezchikovsergey.sem2.spring_mvp.client.ExternalFavoritesClient;
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
public class GatewayFavoritesService {
    private final ExternalFavoritesClient client;

    @RateLimiter(name = EXTERNAL_API_INSTANCE)
    @CircuitBreaker(name = EXTERNAL_API_INSTANCE)
    public void addToFavorites(UUID taskId) {
        client.addToFavorites(taskId);
    }

    @RateLimiter(name = EXTERNAL_API_INSTANCE)
    @CircuitBreaker(name = EXTERNAL_API_INSTANCE)
    public void removeFromFavorites(UUID taskId) {
        client.removeFromFavorites(taskId);
    }

    @RateLimiter(name = EXTERNAL_API_INSTANCE)
    @CircuitBreaker(name = EXTERNAL_API_INSTANCE, fallbackMethod = "fallbackGetFavorites")
    public List<TaskResponseDto> getFavoriteTasks() {
        return client.getFavoriteTasks();
    }

    private List<TaskResponseDto> fallbackGetFavorites(Throwable t) {
        return Collections.emptyList();
    }
}
