package com.mipt.rezchikovsergey.sem2.spring_mvp.controller;

import com.mipt.rezchikovsergey.sem2.spring_mvp.common.model.dto.response.AttachmentResponseDto;
import com.mipt.rezchikovsergey.sem2.spring_mvp.service.GatewayAttachmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
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
public class GatewayAttachmentController {
  private final GatewayAttachmentService attachmentService;

  @PostMapping(
      value = "/tasks/{taskId}/attachments",
      consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  @Operation(summary = "Upload file")
  @ApiResponse(responseCode = "201", description = "File stored")
  @ApiResponse(responseCode = "500", description = "File storage failure")
  public ResponseEntity<AttachmentResponseDto> storeAttachment(
      @PathVariable UUID taskId, @RequestParam("file") MultipartFile file) {
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(attachmentService.storeAttachment(taskId, file));
  }

  @GetMapping("/attachments/{attachmentId}")
  @Operation(summary = "Download file")
  @ApiResponse(responseCode = "200", description = "File content")
  @ApiResponse(responseCode = "404", description = "Attachment not found")
  public ResponseEntity<Resource> downloadAttachment(@PathVariable UUID attachmentId) {
    ResponseEntity<Resource> response = attachmentService.downloadAttachment(attachmentId);

    return ResponseEntity.ok().headers(response.getHeaders()).body(response.getBody());
  }

  @DeleteMapping("/attachments/{attachmentId}")
  @Operation(summary = "Delete attachment")
  @ApiResponse(responseCode = "204", description = "Deleted successfully")
  @ApiResponse(responseCode = "404", description = "Attachment not found")
  public ResponseEntity<String> deleteAttachment(@PathVariable UUID attachmentId) {
    attachmentService.deleteAttachment(attachmentId);
    return ResponseEntity.noContent().build();
  }

  @GetMapping("/tasks/{taskId}/attachments")
  @Operation(
      summary = "List task attachments",
      description =
          "Returns metadata for all files attached to a specific task. Validates task existence first.")
  @ApiResponse(
      responseCode = "200",
      description = "List of attachments (can be empty if task exists but has no files)")
  @ApiResponse(responseCode = "404", description = "Task not found")
  public ResponseEntity<List<AttachmentResponseDto>> getTaskAttachments(@PathVariable UUID taskId) {
    return ResponseEntity.ok(attachmentService.getTaskAttachments(taskId));
  }
}
