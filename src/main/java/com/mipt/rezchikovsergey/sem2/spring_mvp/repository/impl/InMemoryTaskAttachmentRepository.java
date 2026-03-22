package com.mipt.rezchikovsergey.sem2.spring_mvp.repository.impl;

import com.mipt.rezchikovsergey.sem2.spring_mvp.model.entity.TaskAttachment;
import com.mipt.rezchikovsergey.sem2.spring_mvp.repository.TaskAttachmentRepository;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Repository;

@Repository
public class InMemoryTaskAttachmentRepository extends InMemoryRepository<TaskAttachment>
    implements TaskAttachmentRepository {

  @Override
  public List<TaskAttachment> findAttachmentsWithTaskId(UUID taskId) {
    return data.values().stream()
        .filter(attachment -> attachment.getTaskId().equals(taskId))
        .toList();
  }
}
