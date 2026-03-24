package com.mipt.rezchikovsergey.sem2.spring_mvp.service;

import com.mipt.rezchikovsergey.sem2.spring_mvp.config.props.AppProperties;
import com.mipt.rezchikovsergey.sem2.spring_mvp.model.entity.Task;
import com.mipt.rezchikovsergey.sem2.spring_mvp.repository.TaskRepository;
import jakarta.servlet.http.HttpSession;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FavoritesService {
  private final TaskRepository taskRepository;
  private final AppProperties appProperties;

  public void addToFavorites(HttpSession session, UUID taskId) {
    Set<UUID> favorites = getFavorites(session);
    favorites.add(taskId);
    session.setAttribute(getFavoritesKey(), favorites);
  }

  public void removeFromFavorites(HttpSession session, UUID taskId) {
    Set<UUID> favorites = getFavorites(session);
    favorites.remove(taskId);
    session.setAttribute(getFavoritesKey(), favorites);
  }

  public List<Task> getFavoriteTasks(HttpSession session) {
    Set<UUID> favorites = getFavorites(session);
    return favorites.stream()
        .map(taskId -> taskRepository.findById(taskId).orElse(null))
        .filter(Objects::nonNull)
        .toList();
  }

  @SuppressWarnings("unchecked")
  private Set<UUID> getFavorites(HttpSession session) {
    Set<UUID> favorites = (Set<UUID>) session.getAttribute(getFavoritesKey());

    if (favorites == null) {
      return new HashSet<>();
    }

    return favorites;
  }

  private String getFavoritesKey() {
    return appProperties.session().attributes().favoriteTasks();
  }
}
