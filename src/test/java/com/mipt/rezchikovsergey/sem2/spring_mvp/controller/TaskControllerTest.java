package com.mipt.rezchikovsergey.sem2.spring_mvp.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mipt.rezchikovsergey.sem2.spring_mvp.BaseMvcTest;
import com.mipt.rezchikovsergey.sem2.spring_mvp.MyWebMvcTest;
import com.mipt.rezchikovsergey.sem2.spring_mvp.common.exception.task.TaskNotFoundException;
import com.mipt.rezchikovsergey.sem2.spring_mvp.common.model.dto.request.TaskCreateDto;
import com.mipt.rezchikovsergey.sem2.spring_mvp.common.model.dto.request.TaskUpdateDto;
import com.mipt.rezchikovsergey.sem2.spring_mvp.common.model.dto.response.TaskResponseDto;
import com.mipt.rezchikovsergey.sem2.spring_mvp.controller.task.GatewayTaskController;
import com.mipt.rezchikovsergey.sem2.spring_mvp.utils.TaskFactory;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;

@MyWebMvcTest(GatewayTaskController.class)
public class TaskControllerTest extends BaseMvcTest {
  @Autowired private ObjectMapper objectMapper;

  private static final String API_PATH = "/api/tasks";

  @Test
  void getAllTasks_Positive() throws Exception {
    when(taskService.getAllTasks())
        .thenReturn(
            List.of(
                TaskResponseDto.builder().id(TaskFactory.DEFAULT_TASK_ID).title("Test").build()));

    mockMvc
        .perform(get(API_PATH))
        .andExpect(status().isOk())
        .andExpect(header().string("X-Total-Count", "1"))
        .andExpect(jsonPath("$[0].id").value(TaskFactory.DEFAULT_TASK_ID.toString()));
  }

  @Test
  void getAllTasks_InternalError() throws Exception {
    when(taskService.getAllTasks()).thenThrow(new RuntimeException("Fail"));

    mockMvc.perform(get(API_PATH)).andExpect(status().isInternalServerError());
  }

  @Test
  void getTaskById_Positive() throws Exception {
    TaskResponseDto response = TaskResponseDto.builder().id(TaskFactory.DEFAULT_TASK_ID).build();
    when(taskService.getTaskById(TaskFactory.DEFAULT_TASK_ID)).thenReturn(response);

    mockMvc
        .perform(get(API_PATH + "/{id}", TaskFactory.DEFAULT_TASK_ID))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(TaskFactory.DEFAULT_TASK_ID.toString()));
  }

  @Test
  void getTaskById_Nonexistent() throws Exception {
    when(taskService.getTaskById(TaskFactory.DEFAULT_TASK_ID))
        .thenThrow(new TaskNotFoundException(TaskFactory.DEFAULT_TASK_ID));

    mockMvc
        .perform(get(API_PATH + "/{id}", TaskFactory.DEFAULT_TASK_ID))
        .andExpect(status().isNotFound());
  }

  @Test
  void createTask_Positive() throws Exception {
    TaskCreateDto request = TaskFactory.taskCreateDto();
    TaskResponseDto response = TaskResponseDto.builder().id(TaskFactory.DEFAULT_TASK_ID).build();

    when(taskService.createTask(any(TaskCreateDto.class))).thenReturn(response);

    mockMvc
        .perform(
            post(API_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isCreated())
        .andExpect(header().exists("Location"))
        .andExpect(jsonPath("$.id").value(TaskFactory.DEFAULT_TASK_ID.toString()));
  }

  @Test
  void createTask_InternalError() throws Exception {
    when(taskService.createTask(any())).thenThrow(new RuntimeException("Error"));

    mockMvc
        .perform(
            post(API_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(TaskFactory.taskCreateDto())))
        .andExpect(status().isInternalServerError());
  }

  @Test
  void updateTask_Positive() throws Exception {
    mockMvc
        .perform(
            put(API_PATH + "/{id}", TaskFactory.DEFAULT_TASK_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(TaskFactory.taskUpdateDto())))
        .andExpect(status().isNoContent());

    verify(taskService).updateTask(eq(TaskFactory.DEFAULT_TASK_ID), any(TaskUpdateDto.class));
  }

  @Test
  void updateTask_Nonexistent() throws Exception {
    doThrow(new TaskNotFoundException(TaskFactory.DEFAULT_TASK_ID))
        .when(taskService)
        .updateTask(eq(TaskFactory.DEFAULT_TASK_ID), any());

    mockMvc
        .perform(
            put(API_PATH + "/{id}", TaskFactory.DEFAULT_TASK_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(TaskFactory.taskUpdateDto())))
        .andExpect(status().isNotFound());
  }

  @Test
  void removeTask_Positive() throws Exception {
    mockMvc
        .perform(delete(API_PATH + "/{id}", TaskFactory.DEFAULT_TASK_ID))
        .andExpect(status().isNoContent());

    verify(taskService).removeTask(TaskFactory.DEFAULT_TASK_ID);
  }

  @Test
  void removeTask_Nonexistent() throws Exception {
    doThrow(new TaskNotFoundException(TaskFactory.DEFAULT_TASK_ID))
        .when(taskService)
        .removeTask(TaskFactory.DEFAULT_TASK_ID);

    mockMvc
        .perform(delete(API_PATH + "/{id}", TaskFactory.DEFAULT_TASK_ID))
        .andExpect(status().isNotFound());
  }
}
