package com.mipt.rezchikovsergey.sem2.spring_mvp.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.mipt.rezchikovsergey.sem2.spring_mvp.external.model.entity.Task;
import com.mipt.rezchikovsergey.sem2.spring_mvp.external.model.entity.TaskAttachment;
import com.mipt.rezchikovsergey.sem2.spring_mvp.external.repository.TaskAttachmentRepository;
import com.mipt.rezchikovsergey.sem2.spring_mvp.external.repository.TaskRepository;
import com.mipt.rezchikovsergey.sem2.spring_mvp.utils.TaskFactory;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@ActiveProfiles("test")
@Import({TaskAttachmentRepository.class, TaskRepository.class})
class TaskAttachmentRepositoryTest {
  @Autowired private TaskAttachmentRepository attachmentRepository;
  @Autowired private TaskRepository taskRepository;

  private Task savedTask;

  @BeforeEach
  void setUp() {
    savedTask = TaskFactory.task(TaskFactory.DEFAULT_TASK_ID);
    taskRepository.save(savedTask);
  }

  @Test
  void saveAndFindById() {
    TaskAttachment attachment =
        TaskFactory.attachment(savedTask, TaskFactory.DEFAULT_ATTACHMENT_ID);
    attachment.setFilename("test.txt");

    attachmentRepository.save(attachment);
    Optional<TaskAttachment> found = attachmentRepository.findById(attachment.getId());

    assertThat(found).isPresent();
    assertThat(found.get().getFilename()).isEqualTo("test.txt");
    assertThat(found.get().getTask().getId()).isEqualTo(savedTask.getId());
  }

  @Test
  void existsById() {
    TaskAttachment attachment =
        TaskFactory.attachment(savedTask, TaskFactory.DEFAULT_ATTACHMENT_ID);
    attachmentRepository.save(attachment);

    assertThat(attachmentRepository.existsById(attachment.getId())).isTrue();
    assertThat(attachmentRepository.existsById(UUID.randomUUID())).isFalse();
  }

  @Test
  void findByIdReturnsEmptyWhenNotFound() {
    assertThat(attachmentRepository.findById(UUID.randomUUID())).isEmpty();
  }

  @Test
  void removeById() {
    TaskAttachment attachment =
        TaskFactory.attachment(savedTask, TaskFactory.DEFAULT_ATTACHMENT_ID);
    attachmentRepository.save(attachment);

    attachmentRepository.removeById(attachment.getId());

    assertThat(attachmentRepository.existsById(attachment.getId())).isFalse();
  }
}
