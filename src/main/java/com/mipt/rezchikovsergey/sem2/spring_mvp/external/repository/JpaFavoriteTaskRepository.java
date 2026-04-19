package com.mipt.rezchikovsergey.sem2.spring_mvp.external.repository;

import com.mipt.rezchikovsergey.sem2.spring_mvp.external.model.entity.FavoriteTask;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaFavoriteTaskRepository extends JpaRepository<FavoriteTask, UUID> {
  List<FavoriteTask> findAllByUserId(UUID userId);

  Optional<FavoriteTask> findByUserIdAndTaskId(UUID userId, UUID taskId);

  void deleteByUserIdAndTaskId(UUID userId, UUID taskId);

  boolean existsByUserIdAndTaskId(UUID userId, UUID taskId);
}
