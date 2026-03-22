package com.mipt.rezchikovsergey.sem2.spring_mvp.controller;

import com.mipt.rezchikovsergey.sem2.spring_mvp.model.dto.response.AttachmentResponseDto;
import com.mipt.rezchikovsergey.sem2.spring_mvp.model.entity.TaskAttachment;
import com.mipt.rezchikovsergey.sem2.spring_mvp.model.mapper.TaskAttachmentMapper;
import com.mipt.rezchikovsergey.sem2.spring_mvp.service.AttachmentService;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AttachmentController {
  private final AttachmentService attachmentService;
  private final TaskAttachmentMapper attachmentMapper;

  private static final String DOWNLOAD_CONTENT_DISPOSITION = "attachment; filename=\"%s\"";

  @PostMapping(
      value = "/tasks/{taskId}/attachments",
      consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<AttachmentResponseDto> storeAttachment(
      @PathVariable UUID taskId, @RequestParam("file") MultipartFile file) {
    TaskAttachment attachment = attachmentService.storeAttachment(taskId, file);

    AttachmentResponseDto responseDto = attachmentMapper.toResponseDto(attachment);
    return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
  }

  @GetMapping("/attachments/{attachmentId}")
  public ResponseEntity<Resource> downloadAttachment(@PathVariable UUID attachmentId) {
    TaskAttachment attachment = attachmentService.getAttachment(attachmentId);
    Resource resource = attachmentService.loadAsResource(attachmentId);

    return ResponseEntity.status(HttpStatus.OK)
        .contentType(MediaType.parseMediaType(attachment.getContentType()))
        .header(
            HttpHeaders.CONTENT_DISPOSITION,
            DOWNLOAD_CONTENT_DISPOSITION.formatted(attachment.getFilename()))
        .body(resource);
  }

  @DeleteMapping("/attachments/{attachmentId}")
  public ResponseEntity<String> deleteAttachment(@PathVariable UUID attachmentId) {
    attachmentService.deleteAttachment(attachmentId);

    return ResponseEntity.ok().body("Attachment was successfully deleted");
  }

  @GetMapping("/tasks/{taskId}/attachments")
  public ResponseEntity<List<AttachmentResponseDto>> getTaskAttachments(@PathVariable UUID taskId) {
    List<TaskAttachment> attachments = attachmentService.getTaskAttachments(taskId);
    List<AttachmentResponseDto> responseDtos =
        attachments.stream().map(attachmentMapper::toResponseDto).toList();

    return ResponseEntity.ok(responseDtos);
  }
}
