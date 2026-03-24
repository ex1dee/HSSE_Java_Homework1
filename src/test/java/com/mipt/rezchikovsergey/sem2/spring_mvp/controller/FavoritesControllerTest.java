package com.mipt.rezchikovsergey.sem2.spring_mvp.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.mipt.rezchikovsergey.sem2.spring_mvp.BaseMvcTest;
import com.mipt.rezchikovsergey.sem2.spring_mvp.MyWebMvcTest;
import com.mipt.rezchikovsergey.sem2.spring_mvp.exceptions.task.TaskNotFoundException;
import com.mipt.rezchikovsergey.sem2.spring_mvp.model.dto.response.TaskResponseDto;
import com.mipt.rezchikovsergey.sem2.spring_mvp.model.entity.Task;
import com.mipt.rezchikovsergey.sem2.spring_mvp.utils.TaskFactory;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpSession;

@MyWebMvcTest(FavoritesController.class)
public class FavoritesControllerTest extends BaseMvcTest {
  @Test
  void shouldAddToFavoritesWithSession() throws Exception {
    MockHttpSession session = new MockHttpSession();

    mockMvc
        .perform(post("/api/favorites/{taskId}", TaskFactory.DEFAULT_TASK_ID).session(session))
        .andExpect(status().isOk());

    verify(favoritesService).addToFavorites(eq(session), eq(TaskFactory.DEFAULT_TASK_ID));
  }

  @Test
  void shouldReturnFavoritesList() throws Exception {
    MockHttpSession session = new MockHttpSession();
    Task task = Task.builder().id(TaskFactory.DEFAULT_TASK_ID).build();
    TaskResponseDto responseDto = TaskResponseDto.builder().id(task.getId()).build();

    when(favoritesService.getFavoriteTasks(any(HttpSession.class))).thenReturn(List.of(task));
    when(taskMapper.toResponseDto(task)).thenReturn(responseDto);

    mockMvc
        .perform(get("/api/favorites").session(session))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.length()").value(1));
  }

  @Test
  void shouldRemoveFromFavorites() throws Exception {
    MockHttpSession session = new MockHttpSession();

    mockMvc
        .perform(delete("/api/favorites/{taskId}", TaskFactory.DEFAULT_TASK_ID).session(session))
        .andExpect(status().isNoContent());

    verify(favoritesService).removeFromFavorites(eq(session), eq(TaskFactory.DEFAULT_TASK_ID));
  }

  @Test
  void shouldReturn404WhenRemovingNonExistentFavorite() throws Exception {
    MockHttpSession session = new MockHttpSession();

    doThrow(new TaskNotFoundException(TaskFactory.DEFAULT_TASK_ID))
        .when(favoritesService)
        .removeFromFavorites(any(), eq(TaskFactory.DEFAULT_TASK_ID));

    mockMvc
        .perform(delete("/api/favorites/{taskId}", TaskFactory.DEFAULT_TASK_ID).session(session))
        .andExpect(status().isNotFound());
  }

  @Test
  void shouldReturn404WhenAddingNonExistentTask() throws Exception {
    MockHttpSession session = new MockHttpSession();

    doThrow(new TaskNotFoundException(TaskFactory.DEFAULT_TASK_ID))
        .when(favoritesService)
        .addToFavorites(any(), eq(TaskFactory.DEFAULT_TASK_ID));

    mockMvc
        .perform(post("/api/favorites/{taskId}", TaskFactory.DEFAULT_TASK_ID).session(session))
        .andExpect(status().isNotFound());
  }

  @Test
  void shouldReturn204OnDelete() throws Exception {
    MockHttpSession session = new MockHttpSession();

    mockMvc
        .perform(delete("/api/favorites/{taskId}", TaskFactory.DEFAULT_TASK_ID).session(session))
        .andExpect(status().isNoContent());

    verify(favoritesService).removeFromFavorites(any(), eq(TaskFactory.DEFAULT_TASK_ID));
  }
}
