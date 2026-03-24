package com.mipt.rezchikovsergey.sem2.spring_mvp.service;

import com.mipt.rezchikovsergey.sem2.spring_mvp.config.props.AppProperties;
import com.mipt.rezchikovsergey.sem2.spring_mvp.exceptions.task.TaskAttachmentNotFoundException;
import com.mipt.rezchikovsergey.sem2.spring_mvp.exceptions.task.TaskNotFoundException;
import com.mipt.rezchikovsergey.sem2.spring_mvp.exceptions.web.FileReadStreamException;
import com.mipt.rezchikovsergey.sem2.spring_mvp.model.entity.TaskAttachment;
import com.mipt.rezchikovsergey.sem2.spring_mvp.repository.TaskAttachmentRepository;
import com.mipt.rezchikovsergey.sem2.spring_mvp.repository.TaskRepository;
import com.mipt.rezchikovsergey.sem2.spring_mvp.storage.FileStorage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class AttachmentService {
  private final TaskAttachmentRepository attachmentRepository;
  private final TaskRepository taskRepository;
  private final FileStorage fileStorage;
  private final AppProperties appProperties;

  public TaskAttachment storeAttachment(UUID taskId, MultipartFile file) {
    validateTaskExists(taskId);

    TaskAttachment attachment = createTaskAttachment(taskId, file);
    saveFileToStorage(file, attachment.getStoredFilename());
    attachmentRepository.save(attachment);

    return attachment;
  }

  public Resource loadAsResource(UUID attachmentId) {
    TaskAttachment attachment = getAttachment(attachmentId);
    return fileStorage.loadAsResource(getUploadDirectory(), attachment.getStoredFilename());
  }

  public void deleteAttachment(UUID attachmentId) {
    TaskAttachment attachment = getAttachment(attachmentId);
    fileStorage.delete(getUploadDirectory(), attachment.getStoredFilename());
    attachmentRepository.removeById(attachmentId);
  }

  public TaskAttachment getAttachment(UUID attachmentId) {
    return attachmentRepository
        .findById(attachmentId)
        .orElseThrow(() -> new TaskAttachmentNotFoundException(attachmentId));
  }

  public List<TaskAttachment> getTaskAttachments(UUID taskId) {
    validateTaskExists(taskId);

    return attachmentRepository.findAttachmentsWithTaskId(taskId);
  }

  private void validateTaskExists(UUID taskId) {
    if (!taskRepository.existsById(taskId)) {
      throw new TaskNotFoundException(taskId);
    }
  }

  private TaskAttachment createTaskAttachment(UUID taskId, MultipartFile file) {
    UUID attachmentId = UUID.randomUUID();

    return TaskAttachment.builder()
        .id(attachmentId)
        .taskId(taskId)
        .filename(file.getOriginalFilename())
        .storedFilename(attachmentId.toString())
        .contentType(file.getContentType())
        .uploadedAt(LocalDateTime.now())
        .size(file.getSize())
        .build();
  }

  private void saveFileToStorage(MultipartFile file, String storedFilename) {
    try (InputStream inputStream = file.getInputStream()) {
      fileStorage.store(inputStream, getUploadDirectory(), storedFilename);
    } catch (IOException e) {
      throw new FileReadStreamException(file.getOriginalFilename(), e);
    }
  }

  private Path getUploadDirectory() {
    return appProperties.storage().directories().taskAttachments();
  }
}
