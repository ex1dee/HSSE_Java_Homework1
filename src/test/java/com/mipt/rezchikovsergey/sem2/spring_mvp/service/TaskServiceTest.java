package com.mipt.rezchikovsergey.sem2.spring_mvp.service;

import com.mipt.rezchikovsergey.sem2.spring_mvp.common.model.dto.request.TaskUpdateDto;
import com.mipt.rezchikovsergey.sem2.spring_mvp.external.model.entity.Task;
import com.mipt.rezchikovsergey.sem2.spring_mvp.external.model.mapper.TaskMapper;
import com.mipt.rezchikovsergey.sem2.spring_mvp.external.repository.TaskRepository;
import com.mipt.rezchikovsergey.sem2.spring_mvp.external.service.AttachmentService;
import com.mipt.rezchikovsergey.sem2.spring_mvp.external.service.TaskService;
import com.mipt.rezchikovsergey.sem2.spring_mvp.utils.TaskFactory;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE) // зачем?
class TaskServiceTest {
    @Autowired
    private TaskService taskService;

    @MockitoBean
    private TaskRepository taskRepository;
    @MockitoBean
    private AttachmentService attachmentService;
    @MockitoBean
    private TaskMapper taskMapper;

    @Test
    void updateTask_ShouldUpdateStatusAndSave_WhenTaskExists() {
        UUID taskId = UUID.randomUUID();
        Task existingTask = TaskFactory.task(taskId);

        when(taskRepository.findById(taskId)).thenReturn(Optional.of(existingTask));

        TaskUpdateDto updateDto = TaskUpdateDto.builder()
                .title("new title")
                .description("new description")
                .completed(true)
                .dueDate(LocalDate.now().plusDays(1))
                .build();
        taskService.updateTask(taskId, updateDto);

        verify(taskMapper).updateEntity(updateDto, existingTask);

        ArgumentCaptor<Task> taskCaptor = ArgumentCaptor.forClass(Task.class);
        verify(taskRepository).save(taskCaptor.capture());

        Task savedTask = taskCaptor.getValue();
        assertThat(savedTask).isNotNull();
        assertThat(savedTask.getId()).isEqualTo(taskId);
    }
}