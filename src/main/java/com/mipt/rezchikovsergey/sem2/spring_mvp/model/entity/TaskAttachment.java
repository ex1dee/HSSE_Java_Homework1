package com.mipt.rezchikovsergey.sem2.spring_mvp.model.entity;

import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class TaskAttachment extends BaseEntity {
  private UUID taskId;
  private String filename;
  private String storedFilename;
  private String contentType;
  private LocalDateTime uploadedAt;
  private Long size;
}
