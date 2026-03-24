package com.mipt.rezchikovsergey.sem2.spring_mvp.repository;

import com.mipt.rezchikovsergey.sem2.spring_mvp.model.entity.TaskAttachment;
import java.util.List;
import java.util.UUID;

public interface TaskAttachmentRepository extends CrudRepository<TaskAttachment> {
  List<TaskAttachment> findAttachmentsWithTaskId(UUID taskId);
}
