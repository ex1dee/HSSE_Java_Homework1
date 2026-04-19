package com.mipt.rezchikovsergey.sem2.spring_mvp.model.mapper;

import static org.junit.jupiter.api.Assertions.*;

import com.mipt.rezchikovsergey.sem2.spring_mvp.common.model.dto.request.TaskCreateDto;
import com.mipt.rezchikovsergey.sem2.spring_mvp.common.model.dto.request.TaskUpdateDto;
import com.mipt.rezchikovsergey.sem2.spring_mvp.common.model.dto.response.TaskResponseDto;
import com.mipt.rezchikovsergey.sem2.spring_mvp.common.model.enums.TaskPriority;
import com.mipt.rezchikovsergey.sem2.spring_mvp.external.model.entity.Task;
import com.mipt.rezchikovsergey.sem2.spring_mvp.external.model.mapper.TaskMapper;
import com.mipt.rezchikovsergey.sem2.spring_mvp.external.model.mapper.TaskMapperImpl;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class TaskMapperTest {
  private final TaskMapper mapper = new TaskMapperImpl();

  @Test
  void shouldMapCreateDtoToEntity() {
    TaskCreateDto dto =
        TaskCreateDto.builder()
            .title("Test Task")
            .priority(TaskPriority.HIGH)
            .dueDate(LocalDate.now().plusDays(1))
            .tags(Set.of("A", "B"))
            .build();

    Task entity = mapper.toEntity(dto);

    assertNotNull(entity.getId());
    assertNotNull(entity.getCreatedAt());
    assertEquals("Test Task", entity.getTitle());
    assertFalse(entity.isCompleted());
    assertEquals(TaskPriority.HIGH, entity.getPriority());
    assertEquals(2, entity.getTags().size());
  }

  @Test
  void shouldUpdateOnlyPresentFields() {
    Task existingTask = new Task();
    existingTask.setId(UUID.randomUUID());
    existingTask.setTitle("Old Title");
    existingTask.setCompleted(false);

    TaskUpdateDto updateDto = TaskUpdateDto.builder().title("New Title").completed(true).build();

    mapper.updateEntity(updateDto, existingTask);

    assertEquals("New Title", existingTask.getTitle());
    assertTrue(existingTask.isCompleted());
    assertNotNull(existingTask.getId());
  }

  @Test
  void shouldMapEntityToResponseDto() {
    UUID taskId = UUID.randomUUID();
    LocalDateTime now = LocalDateTime.now();

    Task entity = new Task();
    entity.setId(taskId);
    entity.setTitle("Java Homework 2");
    entity.setDescription("Spring");
    entity.setCreatedAt(now);
    entity.setDueDate(LocalDate.now().plusWeeks(1));
    entity.setPriority(TaskPriority.HIGH);
    entity.setTags(Set.of("I", "a", "m", "t", "i", "r", "e", "d", ":", "P"));
    entity.setCompleted(true);

    TaskResponseDto response = mapper.toResponseDto(entity);

    assertEquals(taskId, response.id());
    assertEquals("Java Homework 2", response.title());
    assertEquals(now, response.createdAt());
    assertEquals(TaskPriority.HIGH, response.priority());
    assertTrue(response.completed());
    assertEquals(10, response.tags().size());
    assertTrue(response.tags().contains(":"));
  }
}
