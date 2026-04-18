package com.mipt.rezchikovsergey.sem2.spring_mvp.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.mipt.rezchikovsergey.sem2.spring_mvp.BaseMvcTest;
import com.mipt.rezchikovsergey.sem2.spring_mvp.MyWebMvcTest;
import com.mipt.rezchikovsergey.sem2.spring_mvp.common.exception.task.TaskAttachmentNotFoundException;
import com.mipt.rezchikovsergey.sem2.spring_mvp.common.model.dto.response.AttachmentResponseDto;
import com.mipt.rezchikovsergey.sem2.spring_mvp.utils.TaskFactory;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;

@MyWebMvcTest(GatewayAttachmentController.class)
public class AttachmentControllerTest extends BaseMvcTest {
  @Test
  void shouldUploadFileAndReturnJson() throws Exception {
    MockMultipartFile file =
        new MockMultipartFile("file", "test.txt", MediaType.TEXT_PLAIN_VALUE, "Hello".getBytes());

    AttachmentResponseDto responseDto =
        AttachmentResponseDto.builder()
            .id(TaskFactory.DEFAULT_ATTACHMENT_ID)
            .filename("test.txt")
            .build();

    when(attachmentService.storeAttachment(any(UUID.class), any())).thenReturn(responseDto);

    mockMvc
        .perform(
            multipart("/api/tasks/{taskId}/attachments", TaskFactory.DEFAULT_ATTACHMENT_ID)
                .file(file))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.id").value(responseDto.id().toString()))
        .andExpect(jsonPath("$.filename").value("test.txt"));
  }

  @Test
  void shouldDownloadFileWithCorrectHeaders() throws Exception {
    Resource mockResource = new ByteArrayResource("content".getBytes());

    ResponseEntity<Resource> responseEntity =
        ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"manual.pdf\"")
            .contentType(MediaType.APPLICATION_PDF)
            .body(mockResource);

    when(attachmentService.downloadAttachment(TaskFactory.DEFAULT_ATTACHMENT_ID))
        .thenReturn(responseEntity);

    mockMvc
        .perform(get("/api/attachments/{attachmentId}", TaskFactory.DEFAULT_ATTACHMENT_ID))
        .andExpect(status().isOk())
        .andExpect(
            header().string(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"manual.pdf\""))
        .andExpect(content().bytes("content".getBytes()));
  }

  @Test
  void shouldReturn404WhenDownloadingNonExistentFile() throws Exception {
    when(attachmentService.downloadAttachment(TaskFactory.DEFAULT_ATTACHMENT_ID))
        .thenThrow(new TaskAttachmentNotFoundException(TaskFactory.DEFAULT_ATTACHMENT_ID));

    mockMvc
        .perform(get("/api/attachments/{attachmentId}", TaskFactory.DEFAULT_ATTACHMENT_ID))
        .andExpect(status().isNotFound());
  }

  @Test
  void shouldDeleteAttachment() throws Exception {
    mockMvc
        .perform(delete("/api/attachments/{attachmentId}", TaskFactory.DEFAULT_ATTACHMENT_ID))
        .andExpect(status().isOk())
        .andExpect(content().string("Attachment was successfully deleted"));

    verify(attachmentService).deleteAttachment(TaskFactory.DEFAULT_ATTACHMENT_ID);
  }
}
