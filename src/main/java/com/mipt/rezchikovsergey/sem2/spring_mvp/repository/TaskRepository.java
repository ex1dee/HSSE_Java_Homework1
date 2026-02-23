package com.mipt.rezchikovsergey.sem2.spring_mvp.repository;

import com.mipt.rezchikovsergey.sem2.spring_mvp.model.entity.TaskEntity;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TaskRepository {
  void save(TaskEntity task);

  List<TaskEntity> findAll();

  Optional<TaskEntity> findById(UUID id);

  void removeById(UUID id);
}
