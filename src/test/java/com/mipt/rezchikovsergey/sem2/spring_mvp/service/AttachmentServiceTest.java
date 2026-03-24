package com.mipt.rezchikovsergey.sem2.spring_mvp.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import com.mipt.rezchikovsergey.sem2.spring_mvp.config.props.AppProperties;
import com.mipt.rezchikovsergey.sem2.spring_mvp.exceptions.task.TaskAttachmentNotFoundException;
import com.mipt.rezchikovsergey.sem2.spring_mvp.exceptions.task.TaskNotFoundException;
import com.mipt.rezchikovsergey.sem2.spring_mvp.model.entity.TaskAttachment;
import com.mipt.rezchikovsergey.sem2.spring_mvp.repository.TaskAttachmentRepository;
import com.mipt.rezchikovsergey.sem2.spring_mvp.repository.TaskRepository;
import com.mipt.rezchikovsergey.sem2.spring_mvp.storage.FileStorage;
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
    UUID taskId = UUID.randomUUID();
    MockMultipartFile file =
        new MockMultipartFile("file", "test.png", "image/png", "data".getBytes());
    when(taskRepository.existsById(taskId)).thenReturn(true);

    TaskAttachment result = attachmentService.storeAttachment(taskId, file);

    assertNotNull(result);
    verify(fileStorage).store(any(InputStream.class), eq(testPath), anyString());
    verify(attachmentRepository).save(any(TaskAttachment.class));
  }

  @Test
  void storeAttachment_TaskNotFound() {
    UUID taskId = UUID.randomUUID();
    when(taskRepository.existsById(taskId)).thenReturn(false);

    assertThrows(
        TaskNotFoundException.class,
        () ->
            attachmentService.storeAttachment(
                taskId, new MockMultipartFile("f", "t.txt".getBytes())));
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
