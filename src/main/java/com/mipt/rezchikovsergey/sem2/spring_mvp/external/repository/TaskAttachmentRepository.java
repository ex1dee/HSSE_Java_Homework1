package com.mipt.rezchikovsergey.sem2.spring_mvp.external.repository;

import com.mipt.rezchikovsergey.sem2.spring_mvp.external.model.entity.TaskAttachment;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
public class TaskAttachmentRepository implements CrudRepository<TaskAttachment> {
  private final JpaTaskAttachmentRepository jpaRepository;

  @Override
  @Transactional
  public void save(TaskAttachment entity) {
    jpaRepository.save(entity);
  }

  @Override
  @Transactional(readOnly = true)
  public List<TaskAttachment> findAll() {
    return jpaRepository.findAll();
  }

  @Override
  @Transactional(readOnly = true)
  public Optional<TaskAttachment> findById(UUID id) {
    return jpaRepository.findById(id);
  }

  @Override
  @Transactional
  public void removeById(UUID id) {
    jpaRepository.deleteById(id);
  }

  @Override
  @Transactional(readOnly = true)
  public boolean existsById(UUID id) {
    return jpaRepository.existsById(id);
  }
}
