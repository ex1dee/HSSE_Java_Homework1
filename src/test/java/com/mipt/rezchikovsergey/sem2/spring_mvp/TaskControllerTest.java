package com.mipt.rezchikovsergey.sem2.spring_mvp;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import com.mipt.rezchikovsergey.sem2.spring_mvp.exceptions.TaskNotFoundException;
import com.mipt.rezchikovsergey.sem2.spring_mvp.model.dto.request.CreateTaskRequest;
import com.mipt.rezchikovsergey.sem2.spring_mvp.model.dto.request.UpdateTaskRequest;
import com.mipt.rezchikovsergey.sem2.spring_mvp.model.dto.response.IDResponse;
import com.mipt.rezchikovsergey.sem2.spring_mvp.model.entity.Task;
import com.mipt.rezchikovsergey.sem2.spring_mvp.service.TaskService;
import java.util.List;
import java.util.UUID;
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
  private static final Task TEST_TASK = new Task(UUID.randomUUID(), "title", "description", true);

  @Test
  public void getAllTasks_Positive() {
    when(taskService.getAllTasks()).thenReturn(List.of(TEST_TASK));

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
    when(taskService.getTaskById(TEST_TASK.getId())).thenReturn(TEST_TASK);

    ResponseEntity<Task> response =
        restTemplate.getForEntity(API_PATH + "/" + TEST_TASK.getId(), Task.class);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    Assertions.assertNotNull(response.getBody());
    assertEquals(TEST_TASK.getId(), response.getBody().getId());
  }

  @Test
  public void getTaskById_Nonexistent() {
    UUID id = UUID.randomUUID();
    when(taskService.getTaskById(id)).thenThrow(new TaskNotFoundException(id));

    ResponseEntity<String> response = restTemplate.getForEntity(API_PATH + "/" + id, String.class);

    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
  }

  @Test
  public void createTask_Positive() {
    CreateTaskRequest request = new CreateTaskRequest("title", "description");
    UUID id = UUID.randomUUID();
    when(taskService.createTask(request)).thenReturn(id);

    ResponseEntity<IDResponse> response =
        restTemplate.postForEntity(API_PATH, request, IDResponse.class);

    assertEquals(HttpStatus.CREATED, response.getStatusCode());
    assertNotNull(response.getHeaders().getLocation());
    assertNotNull(response.getBody());
    assertEquals(id, response.getBody().id());
  }

  @Test
  public void createTask_InternalError() {
    CreateTaskRequest request = new CreateTaskRequest("title", "description");
    when(taskService.createTask(request)).thenThrow(new RuntimeException("Some error"));

    ResponseEntity<String> response = restTemplate.postForEntity(API_PATH, request, String.class);

    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
  }

  @Test
  public void updateTask_Positive() {
    UpdateTaskRequest request = new UpdateTaskRequest("title", "description", true);
    UUID id = UUID.randomUUID();
    doNothing().when(taskService).updateTask(id, request);

    HttpEntity<UpdateTaskRequest> entity = new HttpEntity<>(request);
    ResponseEntity<Object> response =
        restTemplate.exchange(API_PATH + "/" + id, HttpMethod.PUT, entity, Object.class);

    assertEquals(HttpStatus.OK, response.getStatusCode());
  }

  @Test
  public void updateTask_Nonexistent() {
    UpdateTaskRequest request = new UpdateTaskRequest("title", "description", true);
    UUID id = UUID.randomUUID();
    doThrow(new TaskNotFoundException(id)).when(taskService).updateTask(id, request);

    HttpEntity<UpdateTaskRequest> entity = new HttpEntity<>(request);
    ResponseEntity<String> response =
        restTemplate.exchange(API_PATH + "/" + id, HttpMethod.PUT, entity, String.class);

    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
  }

  @Test
  public void removeTask_Positive() {
    UUID id = UUID.randomUUID();
    doNothing().when(taskService).removeTask(id);

    ResponseEntity<Object> response =
        restTemplate.exchange(API_PATH + "/" + id, HttpMethod.DELETE, null, Object.class);

    assertEquals(HttpStatus.OK, response.getStatusCode());
  }

  @Test
  public void removeTask_Nonexistent() {
    UUID id = UUID.randomUUID();
    doThrow(new TaskNotFoundException(id)).when(taskService).removeTask(id);

    ResponseEntity<String> response =
        restTemplate.exchange(API_PATH + "/" + id, HttpMethod.DELETE, null, String.class);

    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
  }
}
