package com.mipt.rezchikovsergey.sem2.spring_mvp.external.service;

import com.mipt.rezchikovsergey.sem2.spring_mvp.common.exception.task.TaskNotFoundException;
import com.mipt.rezchikovsergey.sem2.spring_mvp.external.model.entity.FavoriteTask;
import com.mipt.rezchikovsergey.sem2.spring_mvp.external.model.entity.Task;
import com.mipt.rezchikovsergey.sem2.spring_mvp.external.repository.FavoriteTaskRepository;
import com.mipt.rezchikovsergey.sem2.spring_mvp.external.repository.TaskRepository;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FavoritesService {
  private final TaskRepository taskRepository;
  private final FavoriteTaskRepository favoriteTaskRepository;

  @Transactional
  public void addToFavorites(UUID userId, UUID taskId) {
    if (!taskRepository.existsById(taskId)) {
      throw new TaskNotFoundException(taskId);
    }

    if (!favoriteTaskRepository.isFavorite(userId, taskId)) {
      FavoriteTask favorite = FavoriteTask.builder().userId(userId).taskId(taskId).build();
      favoriteTaskRepository.save(favorite);
    }
  }

  @Transactional
  public void removeFromFavorites(UUID userId, UUID taskId) {
    favoriteTaskRepository.removeByUserAndTask(userId, taskId);
  }

  @Transactional(readOnly = true)
  public List<Task> getFavoriteTasks(UUID userId) {
    List<FavoriteTask> favorites = favoriteTaskRepository.findByUserId(userId);

    return favorites.stream()
        .map(fav -> taskRepository.findById(fav.getTaskId()).orElse(null))
        .filter(Objects::nonNull)
        .toList();
  }
}
