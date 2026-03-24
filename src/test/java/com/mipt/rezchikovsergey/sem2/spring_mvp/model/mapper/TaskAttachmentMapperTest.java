package com.mipt.rezchikovsergey.sem2.spring_mvp.model.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.mipt.rezchikovsergey.sem2.spring_mvp.model.dto.response.AttachmentResponseDto;
import com.mipt.rezchikovsergey.sem2.spring_mvp.model.entity.TaskAttachment;
import java.time.LocalDateTime;
import java.util.UUID;
import org.junit.jupiter.api.Test;

public class TaskAttachmentMapperTest {
  private final TaskAttachmentMapper mapper = new TaskAttachmentMapperImpl();

  @Test
  void shouldMapAttachmentToResponseDto() {
    TaskAttachment attachment = new TaskAttachment();
    attachment.setId(UUID.randomUUID());
    attachment.setFilename("report.pdf");
    attachment.setSize(1024L);
    attachment.setUploadedAt(LocalDateTime.now());

    AttachmentResponseDto response = mapper.toResponseDto(attachment);

    assertNotNull(response.id());
    assertEquals("report.pdf", response.filename());
    assertEquals(1024L, response.size());
  }
}
