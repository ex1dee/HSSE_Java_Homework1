package com.mipt.rezchikovsergey.sem2.spring_mvp.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.mipt.rezchikovsergey.sem2.spring_mvp.BaseRepositoryTest;
import com.mipt.rezchikovsergey.sem2.spring_mvp.external.model.entity.Task;
import com.mipt.rezchikovsergey.sem2.spring_mvp.external.repository.TaskRepository;
import com.mipt.rezchikovsergey.sem2.spring_mvp.utils.TaskFactory;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class TaskRepositoryIT extends BaseRepositoryTest {
  @Autowired private TaskRepository taskRepository;

  @Test
  void findAllWithAttachments_ShouldReturnTasks() {
    Task task = TaskFactory.task(TaskFactory.DEFAULT_TASK_ID);
    String expectedTitle = task.getTitle();
    taskRepository.save(task);

    List<Task> result = taskRepository.findAllWithAttachments();

    assertThat(result).isNotEmpty();
    assertThat(result.getFirst().getTitle()).isEqualTo(expectedTitle);
  }
}
