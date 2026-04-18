package com.mipt.rezchikovsergey.sem2.spring_mvp.external.repository;

import com.mipt.rezchikovsergey.sem2.spring_mvp.external.model.entity.FavoriteTask;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
public class FavoriteTaskRepository implements CrudRepository<FavoriteTask> {
  private final JpaFavoriteTaskRepository jpaRepository;

  @Override
  @Transactional
  public void save(FavoriteTask entity) {
    jpaRepository.save(entity);
  }

  @Override
  @Transactional(readOnly = true)
  public List<FavoriteTask> findAll() {
    return jpaRepository.findAll();
  }

  @Override
  @Transactional(readOnly = true)
  public Optional<FavoriteTask> findById(UUID id) {
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

  @Transactional(readOnly = true)
  public List<FavoriteTask> findByUserId(UUID userId) {
    return jpaRepository.findAllByUserId(userId);
  }

  @Transactional
  public void removeByUserAndTask(UUID userId, UUID taskId) {
    jpaRepository.deleteByUserIdAndTaskId(userId, taskId);
  }

  @Transactional(readOnly = true)
  public boolean isFavorite(UUID userId, UUID taskId) {
    return jpaRepository.existsByUserIdAndTaskId(userId, taskId);
  }
}
