package com.mipt.rezchikovsergey.sem2.spring_mvp.service;

import com.mipt.rezchikovsergey.sem2.spring_mvp.config.props.AppProperties;
import com.mipt.rezchikovsergey.sem2.spring_mvp.exceptions.task.TaskAttachmentNotFoundException;
import com.mipt.rezchikovsergey.sem2.spring_mvp.exceptions.task.TaskNotFoundException;
import com.mipt.rezchikovsergey.sem2.spring_mvp.exceptions.web.FileReadStreamException;
import com.mipt.rezchikovsergey.sem2.spring_mvp.model.entity.Task;
import com.mipt.rezchikovsergey.sem2.spring_mvp.model.entity.TaskAttachment;
import com.mipt.rezchikovsergey.sem2.spring_mvp.repository.TaskAttachmentRepository;
import com.mipt.rezchikovsergey.sem2.spring_mvp.repository.TaskRepository;
import com.mipt.rezchikovsergey.sem2.spring_mvp.storage.FileStorage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class AttachmentService {
  private final TaskAttachmentRepository attachmentRepository;
  private final TaskRepository taskRepository;
  private final FileStorage fileStorage;
  private final AppProperties appProperties;

  @Transactional
  public TaskAttachment storeAttachment(UUID taskId, MultipartFile file) {
    Task task = getTask(taskId);
    TaskAttachment attachment = createTaskAttachment(task, file);

    saveFileToStorage(file, attachment.getStoredFilename());
    attachmentRepository.save(attachment);

    return attachment;
  }

  @Transactional(readOnly = true)
  public Resource loadAsResource(UUID attachmentId) {
    TaskAttachment attachment = getAttachment(attachmentId);
    return fileStorage.loadAsResource(getUploadDirectory(), attachment.getStoredFilename());
  }

  @Transactional
  public void deleteAllAttachments(Task task) {
    task.getAttachments().forEach(this::deleteFromStorage);
    task.getAttachments().clear();
  }

  @Transactional
  public void deleteAttachment(UUID attachmentId) {
    TaskAttachment attachment = getAttachment(attachmentId);
    attachmentRepository.removeById(attachmentId);
    deleteFromStorage(attachment);
  }

  @Transactional(readOnly = true)
  public List<TaskAttachment> getTaskAttachments(UUID taskId) {
    Task task = getTask(taskId);
    return task.getAttachments();
  }

  @Transactional(readOnly = true)
  public TaskAttachment getAttachment(UUID attachmentId) {
    return attachmentRepository
        .findById(attachmentId)
        .orElseThrow(() -> new TaskAttachmentNotFoundException(attachmentId));
  }

  private Task getTask(UUID taskId) {
    return taskRepository.findById(taskId).orElseThrow(() -> new TaskNotFoundException(taskId));
  }

  private void deleteFromStorage(TaskAttachment attachment) {
    fileStorage.delete(getUploadDirectory(), attachment.getStoredFilename());
  }

  private TaskAttachment createTaskAttachment(Task task, MultipartFile file) {
    UUID attachmentId = UUID.randomUUID();

    return TaskAttachment.builder()
        .id(attachmentId)
        .task(task)
        .filename(file.getOriginalFilename())
        .storedFilename(attachmentId.toString())
        .contentType(file.getContentType())
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
