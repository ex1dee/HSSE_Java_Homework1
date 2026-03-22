package com.mipt.rezchikovsergey.sem2.spring_mvp;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import com.mipt.rezchikovsergey.sem2.spring_mvp.exceptions.task.TaskNotFoundException;
import com.mipt.rezchikovsergey.sem2.spring_mvp.model.dto.request.TaskCreateDto;
import com.mipt.rezchikovsergey.sem2.spring_mvp.model.dto.request.TaskUpdateDto;
import com.mipt.rezchikovsergey.sem2.spring_mvp.model.dto.response.TaskResponseDto;
import com.mipt.rezchikovsergey.sem2.spring_mvp.model.entity.Task;
import com.mipt.rezchikovsergey.sem2.spring_mvp.service.TaskService;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class TaskControllerTest {
  @Autowired private TestRestTemplate restTemplate;

  @MockitoBean private TaskService taskService;

  private static final String API_PATH = "/api/tasks";

  @Test
  public void getAllTasks_Positive() {
    when(taskService.getAllTasks()).thenReturn(List.of(TaskFactory.task(TaskFactory.DEFAULT_ID)));

    ResponseEntity<Task[]> response = restTemplate.getForEntity(API_PATH, Task[].class);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    Assertions.assertNotNull(response.getBody());
    assertTrue(response.getBody().length > 0);
  }

  @Test
  public void getAllTasks_InternalError() {
    when(taskService.getAllTasks()).thenThrow(new RuntimeException("Something failed"));

    ResponseEntity<String> response = restTemplate.getForEntity(API_PATH, String.class);

    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
  }

  @Test
  public void getTaskById_Positive() {
    Task testTask = TaskFactory.task(TaskFactory.DEFAULT_ID);
    when(taskService.getTaskById(testTask.getId())).thenReturn(testTask);

    ResponseEntity<Task> response =
        restTemplate.getForEntity(API_PATH + "/" + testTask.getId(), Task.class);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    Assertions.assertNotNull(response.getBody());
    assertEquals(testTask.getId(), response.getBody().getId());
  }

  @Test
  public void getTaskById_Nonexistent() {
    when(taskService.getTaskById(TaskFactory.DEFAULT_ID))
        .thenThrow(new TaskNotFoundException(TaskFactory.DEFAULT_ID));

    ResponseEntity<String> response =
        restTemplate.getForEntity(API_PATH + "/" + TaskFactory.DEFAULT_ID, String.class);

    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
  }

  @Test
  public void createTask_Positive() {
    TaskCreateDto request = TaskFactory.taskCreateDto();
    when(taskService.createTask(request))
        .thenReturn(TaskFactory.taskResponseDto(TaskFactory.DEFAULT_ID, request));

    ResponseEntity<TaskResponseDto> response =
        restTemplate.postForEntity(API_PATH, request, TaskResponseDto.class);

    assertEquals(HttpStatus.CREATED, response.getStatusCode());
    assertNotNull(response.getHeaders().getLocation());
    assertNotNull(response.getBody());
    assertEquals(TaskFactory.DEFAULT_ID, response.getBody().id());
  }

  @Test
  public void createTask_InternalError() {
    TaskCreateDto request = TaskFactory.taskCreateDto();
    when(taskService.createTask(request)).thenThrow(new RuntimeException("Some error"));

    ResponseEntity<String> response = restTemplate.postForEntity(API_PATH, request, String.class);

    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
  }

  @Test
  public void updateTask_Positive() {
    TaskUpdateDto request = TaskFactory.taskUpdateDto();
    doNothing().when(taskService).updateTask(TaskFactory.DEFAULT_ID, request);

    HttpEntity<TaskUpdateDto> entity = new HttpEntity<>(request);
    ResponseEntity<Object> response =
        restTemplate.exchange(
            API_PATH + "/" + TaskFactory.DEFAULT_ID, HttpMethod.PUT, entity, Object.class);

    assertEquals(HttpStatus.OK, response.getStatusCode());
  }

  @Test
  public void updateTask_Nonexistent() {
    TaskUpdateDto request = TaskFactory.taskUpdateDto();
    doThrow(new TaskNotFoundException(TaskFactory.DEFAULT_ID))
        .when(taskService)
        .updateTask(TaskFactory.DEFAULT_ID, request);

    HttpEntity<TaskUpdateDto> entity = new HttpEntity<>(request);
    ResponseEntity<String> response =
        restTemplate.exchange(
            API_PATH + "/" + TaskFactory.DEFAULT_ID, HttpMethod.PUT, entity, String.class);

    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
  }

  @Test
  public void removeTask_Positive() {
    doNothing().when(taskService).removeTask(TaskFactory.DEFAULT_ID);

    ResponseEntity<Object> response =
        restTemplate.exchange(
            API_PATH + "/" + TaskFactory.DEFAULT_ID, HttpMethod.DELETE, null, Object.class);

    assertEquals(HttpStatus.OK, response.getStatusCode());
  }

  @Test
  public void removeTask_Nonexistent() {
    doThrow(new TaskNotFoundException(TaskFactory.DEFAULT_ID))
        .when(taskService)
        .removeTask(TaskFactory.DEFAULT_ID);

    ResponseEntity<String> response =
        restTemplate.exchange(
            API_PATH + "/" + TaskFactory.DEFAULT_ID, HttpMethod.DELETE, null, String.class);

    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
  }
}
