package com.mipt.rezchikovsergey.sem2.spring_mvp.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.mipt.rezchikovsergey.sem2.spring_mvp.config.props.AppProperties;
import com.mipt.rezchikovsergey.sem2.spring_mvp.exceptions.task.TaskNotFoundException;
import com.mipt.rezchikovsergey.sem2.spring_mvp.model.entity.Task;
import com.mipt.rezchikovsergey.sem2.spring_mvp.repository.JpaTaskRepository;
import jakarta.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class FavoritesServiceTest {

  @Mock private JpaTaskRepository taskRepository;

  @Mock private AppProperties appProperties;

  @Mock private HttpSession session;

  @InjectMocks private FavoritesService favoritesService;

  private static final String FAV_KEY = "favoriteTasksIds";

  @BeforeEach
  void setUp() {
    lenient().when(appProperties.session()).thenReturn(mock(AppProperties.Session.class));
    lenient()
        .when(appProperties.session().attributes())
        .thenReturn(mock(AppProperties.Session.Attributes.class));
    lenient().when(appProperties.session().attributes().favoriteTasks()).thenReturn(FAV_KEY);
  }

  @SuppressWarnings("unchecked")
  @Test
  void addToFavorites_ShouldAddNewId_WhenTaskExists() {
    UUID taskId = UUID.randomUUID();
    when(taskRepository.existsById(taskId)).thenReturn(true);
    when(session.getAttribute(FAV_KEY)).thenReturn(null);

    favoritesService.addToFavorites(session, taskId);

    verify(session)
        .setAttribute(
            eq(FAV_KEY),
            argThat(
                argument -> {
                  Set<UUID> set = (Set<UUID>) argument;
                  return set.contains(taskId);
                }));
  }

  @Test
  void addToFavorites_ShouldThrowException_WhenTaskDoesNotExist() {
    UUID taskId = UUID.randomUUID();
    when(taskRepository.existsById(taskId)).thenReturn(false);

    assertThrows(
        TaskNotFoundException.class, () -> favoritesService.addToFavorites(session, taskId));
    verify(session, never()).setAttribute(anyString(), any());
  }

  @SuppressWarnings("unchecked")
  @Test
  void removeFromFavorites_ShouldRemoveIdFromSession() {
    UUID taskId = UUID.randomUUID();
    Set<UUID> currentFavorites = new HashSet<>(Collections.singletonList(taskId));
    when(session.getAttribute(FAV_KEY)).thenReturn(currentFavorites);

    favoritesService.removeFromFavorites(session, taskId);

    verify(session)
        .setAttribute(
            eq(FAV_KEY),
            argThat(
                argument -> {
                  Set<UUID> set = (Set<UUID>) argument;
                  return !set.contains(taskId);
                }));
  }

  @Test
  void getFavoriteTasks_ShouldReturnOnlyExistingTasks() {
    UUID id1 = UUID.randomUUID();
    UUID id2 = UUID.randomUUID();
    Set<UUID> favoriteIds = new HashSet<>(Arrays.asList(id1, id2));

    Task task1 = new Task();
    task1.setId(id1);

    when(session.getAttribute(FAV_KEY)).thenReturn(favoriteIds);
    when(taskRepository.findById(id1)).thenReturn(Optional.of(task1));
    when(taskRepository.findById(id2)).thenReturn(Optional.empty());

    List<Task> result = favoritesService.getFavoriteTasks(session);

    assertEquals(1, result.size());
    assertEquals(id1, result.getFirst().getId());
  }

  @Test
  void getFavoriteTasks_ShouldReturnEmptyList_WhenNoFavoritesInSession() {
    when(session.getAttribute(FAV_KEY)).thenReturn(null);

    List<Task> result = favoritesService.getFavoriteTasks(session);

    assertTrue(result.isEmpty());
    verify(taskRepository, never()).findById(any());
  }
}
