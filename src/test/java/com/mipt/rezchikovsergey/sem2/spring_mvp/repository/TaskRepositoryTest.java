package com.mipt.rezchikovsergey.sem2.spring_mvp.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.mipt.rezchikovsergey.sem2.spring_mvp.common.model.dto.TaskPriorityCountDto;
import com.mipt.rezchikovsergey.sem2.spring_mvp.common.model.enums.TaskPriority;
import com.mipt.rezchikovsergey.sem2.spring_mvp.external.model.entity.Task;
import com.mipt.rezchikovsergey.sem2.spring_mvp.external.model.entity.TaskAttachment;
import com.mipt.rezchikovsergey.sem2.spring_mvp.external.repository.TaskRepository;
import com.mipt.rezchikovsergey.sem2.spring_mvp.utils.TaskFactory;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@ActiveProfiles("test")
@Import(TaskRepository.class)
class TaskRepositoryTest {
  @Autowired private TestEntityManager entityManager;
  @Autowired private TaskRepository taskRepository;

  @Test
  void saveAndFindById() {
    Task task = TaskFactory.task(TaskFactory.DEFAULT_TASK_ID);
    task.setTitle("Test Task");

    taskRepository.save(task);
    Optional<Task> found = taskRepository.findById(task.getId());

    assertThat(found).isPresent();
    assertThat(found.get().getTitle()).isEqualTo("Test Task");
  }

  @Test
  void getTasksCountByPriority() {
    Task high1 = TaskFactory.task(UUID.randomUUID());
    high1.setPriority(TaskPriority.HIGH);
    Task high2 = TaskFactory.task(UUID.randomUUID());
    high2.setPriority(TaskPriority.HIGH);
    Task low = TaskFactory.task(UUID.randomUUID());
    low.setPriority(TaskPriority.LOW);

    taskRepository.save(high1);
    taskRepository.save(high2);
    taskRepository.save(low);
    entityManager.flush();

    List<TaskPriorityCountDto> stats = taskRepository.getTasksCountByPriority();

    assertThat(stats).hasSize(2);
    assertThat(stats)
        .extracting(TaskPriorityCountDto::priority)
        .containsExactlyInAnyOrder(TaskPriority.HIGH, TaskPriority.LOW);

    assertThat(
            stats.stream()
                .filter(s -> s.priority() == TaskPriority.HIGH)
                .findFirst()
                .orElseThrow()
                .count())
        .isEqualTo(2L);
  }

  @Test
  void findTasksDueInNextWeek() {
    Task soon = TaskFactory.task(UUID.randomUUID());
    soon.setTitle("Test Task");
    soon.setDueDate(LocalDate.now().plusDays(2));

    Task far = TaskFactory.task(UUID.randomUUID());
    far.setDueDate(LocalDate.now().plusDays(10));

    taskRepository.save(soon);
    taskRepository.save(far);

    List<Task> result = taskRepository.findTasksDueInNextWeek();

    assertThat(result).hasSize(1);
    assertThat(result.getFirst().getTitle()).isEqualTo("Test Task");
  }

  @Test
  void findByCompletedAndPriority() {
    Task task = TaskFactory.task(TaskFactory.DEFAULT_TASK_ID);
    task.setCompleted(true);
    task.setPriority(TaskPriority.LOW);
    taskRepository.save(task);

    Optional<Task> found = taskRepository.findByCompletedAndPriority(true, TaskPriority.LOW);

    assertThat(found).isPresent();
    assertThat(found.get().isCompleted()).isTrue();
    assertThat(found.get().getPriority()).isEqualTo(TaskPriority.LOW);
  }

  @Test
  void existsById() {
    Task task = TaskFactory.task(TaskFactory.DEFAULT_TASK_ID);
    taskRepository.save(task);

    assertThat(taskRepository.existsById(task.getId())).isTrue();
    assertThat(taskRepository.existsById(UUID.randomUUID())).isFalse();
  }

  @Test
  void removeById() {
    Task task = TaskFactory.task(TaskFactory.DEFAULT_TASK_ID);
    taskRepository.save(task);

    taskRepository.removeById(task.getId());

    assertThat(taskRepository.existsById(task.getId())).isFalse();
  }

  @Test
  void shouldSaveTaskWithAttachmentsCascaded() {
    Task task = TaskFactory.task(TaskFactory.DEFAULT_TASK_ID);
    TaskAttachment attachment = TaskFactory.attachment(task, TaskFactory.DEFAULT_ATTACHMENT_ID);
    attachment.setFilename("manual.pdf");

    task.getAttachments().add(attachment);

    taskRepository.save(task);
    entityManager.flush();
    entityManager.clear();

    Optional<Task> foundTask = taskRepository.findById(task.getId());
    assertThat(foundTask).isPresent();

    assertThat(foundTask.get().getAttachments()).hasSize(1);
    assertThat(foundTask.get().getAttachments().getFirst().getFilename()).isEqualTo("manual.pdf");
  }
}
