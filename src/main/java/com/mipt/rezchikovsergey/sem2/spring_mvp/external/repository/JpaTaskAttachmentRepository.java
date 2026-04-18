package com.mipt.rezchikovsergey.sem2.spring_mvp.external.repository;

import com.mipt.rezchikovsergey.sem2.spring_mvp.external.model.entity.TaskAttachment;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaTaskAttachmentRepository extends JpaRepository<TaskAttachment, UUID> {}
