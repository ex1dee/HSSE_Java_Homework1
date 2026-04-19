package com.mipt.rezchikovsergey.sem2.spring_mvp.controller;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.mipt.rezchikovsergey.sem2.spring_mvp.BaseMvcTest;
import com.mipt.rezchikovsergey.sem2.spring_mvp.MyWebMvcTest;
import com.mipt.rezchikovsergey.sem2.spring_mvp.common.exception.task.TaskNotFoundException;
import com.mipt.rezchikovsergey.sem2.spring_mvp.common.model.dto.response.TaskResponseDto;
import com.mipt.rezchikovsergey.sem2.spring_mvp.controller.task.GatewayFavoritesController;
import com.mipt.rezchikovsergey.sem2.spring_mvp.utils.TaskFactory;
import com.mipt.rezchikovsergey.sem2.spring_mvp.utils.TestAuthUtils;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;

@MyWebMvcTest(GatewayFavoritesController.class)
public class FavoritesControllerTest extends BaseMvcTest {
  @Test
  void shouldAddToFavoritesWithSession() throws Exception {
    UUID userId = TestAuthUtils.getContextUserId();

    mockMvc
        .perform(post("/api/favorites/{taskId}", TaskFactory.DEFAULT_TASK_ID))
        .andExpect(status().isOk());

    verify(favoritesService).addToFavorites(eq(userId), eq(TaskFactory.DEFAULT_TASK_ID));
  }

  @Test
  void shouldReturnFavoritesList() throws Exception {
    UUID userId = TestAuthUtils.getContextUserId();
    TaskResponseDto responseDto =
        TaskResponseDto.builder().id(TaskFactory.DEFAULT_TASK_ID).title("Favorite Task").build();

    when(favoritesService.getFavoriteTasks(userId)).thenReturn(List.of(responseDto));

    mockMvc
        .perform(get("/api/favorites"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.length()").value(1));
  }

  @Test
  void shouldRemoveFromFavorites() throws Exception {
    UUID userId = TestAuthUtils.getContextUserId();

    mockMvc
        .perform(delete("/api/favorites/{taskId}", TaskFactory.DEFAULT_TASK_ID))
        .andExpect(status().isNoContent());

    verify(favoritesService).removeFromFavorites(eq(userId), eq(TaskFactory.DEFAULT_TASK_ID));
  }

  @Test
  void shouldReturn404WhenRemovingNonExistentFavorite() throws Exception {
    UUID userId = TestAuthUtils.getContextUserId();

    doThrow(new TaskNotFoundException(TaskFactory.DEFAULT_TASK_ID))
        .when(favoritesService)
        .removeFromFavorites(eq(userId), eq(TaskFactory.DEFAULT_TASK_ID));

    mockMvc
        .perform(delete("/api/favorites/{taskId}", TaskFactory.DEFAULT_TASK_ID))
        .andExpect(status().isNotFound());
  }

  @Test
  void shouldReturn404WhenAddingNonExistentTask() throws Exception {
    UUID userId = TestAuthUtils.getContextUserId();

    doThrow(new TaskNotFoundException(TaskFactory.DEFAULT_TASK_ID))
        .when(favoritesService)
        .addToFavorites(eq(userId), eq(TaskFactory.DEFAULT_TASK_ID));

    mockMvc
        .perform(post("/api/favorites/{taskId}", TaskFactory.DEFAULT_TASK_ID))
        .andExpect(status().isNotFound());
  }

  @Test
  void shouldReturn204OnDelete() throws Exception {
    UUID userId = TestAuthUtils.getContextUserId();

    mockMvc
        .perform(delete("/api/favorites/{taskId}", TaskFactory.DEFAULT_TASK_ID))
        .andExpect(status().isNoContent());

    verify(favoritesService).removeFromFavorites(eq(userId), eq(TaskFactory.DEFAULT_TASK_ID));
  }
}
