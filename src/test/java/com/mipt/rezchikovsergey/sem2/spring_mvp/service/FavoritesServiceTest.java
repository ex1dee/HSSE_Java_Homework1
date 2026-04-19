package com.mipt.rezchikovsergey.sem2.spring_mvp.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.mipt.rezchikovsergey.sem2.spring_mvp.common.exception.task.TaskNotFoundException;
import com.mipt.rezchikovsergey.sem2.spring_mvp.external.model.entity.FavoriteTask;
import com.mipt.rezchikovsergey.sem2.spring_mvp.external.model.entity.Task;
import com.mipt.rezchikovsergey.sem2.spring_mvp.external.repository.FavoriteTaskRepository;
import com.mipt.rezchikovsergey.sem2.spring_mvp.external.repository.TaskRepository;
import com.mipt.rezchikovsergey.sem2.spring_mvp.external.service.FavoritesService;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class FavoritesServiceTest {
  @Mock private TaskRepository taskRepository;
  @Mock private FavoriteTaskRepository favoriteTaskRepository;

  @InjectMocks private FavoritesService favoritesService;

  private UUID userId;
  private UUID taskId;

  @BeforeEach
  void setUp() {
    userId = UUID.randomUUID();
    taskId = UUID.randomUUID();
  }

  @Test
  void addToFavorites_ShouldSave_WhenTaskExistsAndNotAlreadyFavorite() {
    when(taskRepository.existsById(taskId)).thenReturn(true);
    when(favoriteTaskRepository.isFavorite(userId, taskId)).thenReturn(false);

    favoritesService.addToFavorites(userId, taskId);

    verify(favoriteTaskRepository).save(any(FavoriteTask.class));
  }

  @Test
  void addToFavorites_ShouldNotSave_WhenAlreadyInFavorites() {
    when(taskRepository.existsById(taskId)).thenReturn(true);
    when(favoriteTaskRepository.isFavorite(userId, taskId)).thenReturn(true);

    favoritesService.addToFavorites(userId, taskId);

    verify(favoriteTaskRepository, never()).save(any());
  }

  @Test
  void addToFavorites_ShouldThrowException_WhenTaskDoesNotExist() {
    when(taskRepository.existsById(taskId)).thenReturn(false);

    assertThrows(
        TaskNotFoundException.class, () -> favoritesService.addToFavorites(userId, taskId));

    verify(favoriteTaskRepository, never()).save(any());
  }

  @Test
  void removeFromFavorites_ShouldCallRepositoryDelete() {
    favoritesService.removeFromFavorites(userId, taskId);

    verify(favoriteTaskRepository).removeByUserAndTask(userId, taskId);
  }

  @Test
  void getFavoriteTasks_ShouldReturnMappedTasks() {
    Task task = new Task();
    task.setId(taskId);
    FavoriteTask favoriteTask = FavoriteTask.builder().userId(userId).taskId(taskId).build();

    when(favoriteTaskRepository.findByUserId(userId)).thenReturn(List.of(favoriteTask));
    when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));

    List<Task> result = favoritesService.getFavoriteTasks(userId);

    assertEquals(1, result.size());
    assertEquals(taskId, result.getFirst().getId());
  }

  @Test
  void getFavoriteTasks_ShouldReturnEmptyList_WhenNoFavoritesInDb() {
    when(favoriteTaskRepository.findByUserId(userId)).thenReturn(Collections.emptyList());

    List<Task> result = favoritesService.getFavoriteTasks(userId);

    assertTrue(result.isEmpty());
    verify(taskRepository, never()).findById(any());
  }
}
