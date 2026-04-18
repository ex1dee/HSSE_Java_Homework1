package com.mipt.rezchikovsergey.sem2.spring_mvp.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.mipt.rezchikovsergey.sem2.spring_mvp.common.exception.task.TaskAttachmentNotFoundException;
import com.mipt.rezchikovsergey.sem2.spring_mvp.common.exception.task.TaskNotFoundException;
import com.mipt.rezchikovsergey.sem2.spring_mvp.config.props.AppProperties;
import com.mipt.rezchikovsergey.sem2.spring_mvp.external.model.entity.Task;
import com.mipt.rezchikovsergey.sem2.spring_mvp.external.model.entity.TaskAttachment;
import com.mipt.rezchikovsergey.sem2.spring_mvp.external.repository.TaskAttachmentRepository;
import com.mipt.rezchikovsergey.sem2.spring_mvp.external.repository.TaskRepository;
import com.mipt.rezchikovsergey.sem2.spring_mvp.external.service.AttachmentService;
import com.mipt.rezchikovsergey.sem2.spring_mvp.external.storage.FileStorage;
import com.mipt.rezchikovsergey.sem2.spring_mvp.utils.TaskFactory;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.Resource;
import org.springframework.mock.web.MockMultipartFile;

@ExtendWith(MockitoExtension.class)
class AttachmentServiceTest {

  @Mock private TaskAttachmentRepository attachmentRepository;
  @Mock private TaskRepository taskRepository;
  @Mock private FileStorage fileStorage;
  @Mock private AppProperties appProperties;

  @InjectMocks private AttachmentService attachmentService;

  private final Path testPath = Path.of("uploads");

  @BeforeEach
  void setUp() {
    AppProperties.Storage storage = mock(AppProperties.Storage.class);
    AppProperties.Storage.Directories dirs = mock(AppProperties.Storage.Directories.class);
    lenient().when(appProperties.storage()).thenReturn(storage);
    lenient().when(storage.directories()).thenReturn(dirs);
    lenient().when(dirs.taskAttachments()).thenReturn(testPath);
  }

  @Test
  void storeAttachment_Success() {
    Task task = TaskFactory.task(TaskFactory.DEFAULT_TASK_ID);
    MockMultipartFile file =
        new MockMultipartFile("file", "test.png", "image/png", "data".getBytes());
    when(taskRepository.findById(task.getId())).thenReturn(Optional.of(task));

    TaskAttachment result = attachmentService.storeAttachment(task.getId(), file);

    assertNotNull(result);
    verify(fileStorage).store(any(InputStream.class), eq(testPath), anyString());
    verify(attachmentRepository).save(any(TaskAttachment.class));
  }

  @Test
  void storeAttachment_TaskNotFound() {
    Task task = TaskFactory.task(TaskFactory.DEFAULT_TASK_ID);
    when(taskRepository.findById(task.getId())).thenReturn(Optional.empty());

    assertThrows(
        TaskNotFoundException.class,
        () ->
            attachmentService.storeAttachment(
                task.getId(), new MockMultipartFile("f", "t.txt".getBytes())));
    verify(fileStorage, never()).store(any(), any(), any());
  }

  @Test
  void loadAsResource_Success() {
    UUID id = UUID.randomUUID();
    TaskAttachment attachment = TaskAttachment.builder().storedFilename("file").build();
    when(attachmentRepository.findById(id)).thenReturn(Optional.of(attachment));
    when(fileStorage.loadAsResource(testPath, "file")).thenReturn(mock(Resource.class));

    assertNotNull(attachmentService.loadAsResource(id));
  }

  @Test
  void deleteAttachment_Success() {
    UUID id = UUID.randomUUID();
    TaskAttachment attachment = TaskAttachment.builder().storedFilename("file").build();
    when(attachmentRepository.findById(id)).thenReturn(Optional.of(attachment));

    attachmentService.deleteAttachment(id);

    verify(fileStorage).delete(testPath, "file");
    verify(attachmentRepository).removeById(id);
  }

  @Test
  void getAttachment_NotFound() {
    UUID id = UUID.randomUUID();
    when(attachmentRepository.findById(id)).thenReturn(Optional.empty());

    assertThrows(TaskAttachmentNotFoundException.class, () -> attachmentService.getAttachment(id));
  }
}
