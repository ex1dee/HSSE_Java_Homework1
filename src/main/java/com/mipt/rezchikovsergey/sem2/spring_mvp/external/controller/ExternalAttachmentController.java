package com.mipt.rezchikovsergey.sem2.spring_mvp.external.controller;

import com.mipt.rezchikovsergey.sem2.spring_mvp.common.model.dto.response.AttachmentResponseDto;
import com.mipt.rezchikovsergey.sem2.spring_mvp.external.model.entity.TaskAttachment;
import com.mipt.rezchikovsergey.sem2.spring_mvp.external.model.mapper.TaskAttachmentMapper;
import com.mipt.rezchikovsergey.sem2.spring_mvp.external.service.AttachmentService;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/external/v1")
@RequiredArgsConstructor
public class ExternalAttachmentController {
  private final AttachmentService attachmentService;
  private final TaskAttachmentMapper attachmentMapper;

  private static final String DOWNLOAD_CONTENT_DISPOSITION = "attachment; filename=\"%s\"";

  @PostMapping(
      value = "/tasks/{taskId}/attachments",
      consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  @ResponseStatus(HttpStatus.CREATED)
  public AttachmentResponseDto storeAttachment(
      @PathVariable UUID taskId, @RequestParam("file") MultipartFile file) {
    TaskAttachment attachment = attachmentService.storeAttachment(taskId, file);
    return attachmentMapper.toResponseDto(attachment);
  }

  @GetMapping("/attachments/{attachmentId}")
  public ResponseEntity<Resource> downloadAttachment(@PathVariable UUID attachmentId) {
    TaskAttachment attachment = attachmentService.getAttachment(attachmentId);
    Resource resource = attachmentService.loadAsResource(attachmentId);

    return ResponseEntity.ok()
        .contentType(MediaType.parseMediaType(attachment.getContentType()))
        .header(
            HttpHeaders.CONTENT_DISPOSITION,
            DOWNLOAD_CONTENT_DISPOSITION.formatted(attachment.getFilename()))
        .body(resource);
  }

  @DeleteMapping("/attachments/{attachmentId}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteAttachment(@PathVariable UUID attachmentId) {
    attachmentService.deleteAttachment(attachmentId);
  }

  @GetMapping("/tasks/{taskId}/attachments")
  public List<AttachmentResponseDto> getTaskAttachments(@PathVariable UUID taskId) {
    return attachmentService.getTaskAttachments(taskId).stream()
        .map(attachmentMapper::toResponseDto)
        .toList();
  }
}
