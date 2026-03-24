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
import com.mipt.rezchikovsergey.sem2.spring_mvp.exceptions.task.TaskAttachmentNotFoundException;
import com.mipt.rezchikovsergey.sem2.spring_mvp.model.dto.response.AttachmentResponseDto;
import com.mipt.rezchikovsergey.sem2.spring_mvp.model.entity.TaskAttachment;
import com.mipt.rezchikovsergey.sem2.spring_mvp.utils.TaskFactory;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

@MyWebMvcTest(AttachmentController.class)
public class AttachmentControllerTest extends BaseMvcTest {
  @Test
  void shouldUploadFileAndReturnJson() throws Exception {
    MockMultipartFile file =
        new MockMultipartFile("file", "test.txt", MediaType.TEXT_PLAIN_VALUE, "Hello".getBytes());

    TaskAttachment attachment = new TaskAttachment();
    attachment.setId(TaskFactory.DEFAULT_ATTACHMENT_ID);
    attachment.setFilename("test.txt");

    AttachmentResponseDto responseDto =
        AttachmentResponseDto.builder().id(attachment.getId()).filename("test.txt").build();

    when(attachmentService.storeAttachment(any(UUID.class), any())).thenReturn(attachment);
    when(attachmentMapper.toResponseDto(attachment)).thenReturn(responseDto);

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
    TaskAttachment attachment = new TaskAttachment();
    attachment.setFilename("manual.pdf");
    attachment.setContentType("application/pdf");

    Resource mockResource = new ByteArrayResource("content".getBytes());

    when(attachmentService.getAttachment(TaskFactory.DEFAULT_ATTACHMENT_ID)).thenReturn(attachment);
    when(attachmentService.loadAsResource(TaskFactory.DEFAULT_ATTACHMENT_ID))
        .thenReturn(mockResource);

    mockMvc
        .perform(get("/api/attachments/{attachmentId}", TaskFactory.DEFAULT_ATTACHMENT_ID))
        .andExpect(status().isOk())
        .andExpect(
            header().string(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"manual.pdf\""))
        .andExpect(content().bytes("content".getBytes()));
  }

  @Test
  void shouldReturn404WhenDownloadingNonExistentFile() throws Exception {
    when(attachmentService.getAttachment(TaskFactory.DEFAULT_ATTACHMENT_ID))
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
