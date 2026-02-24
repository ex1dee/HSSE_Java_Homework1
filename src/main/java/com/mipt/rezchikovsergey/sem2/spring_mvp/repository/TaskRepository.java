package com.mipt.rezchikovsergey.sem2.spring_mvp.repository;

import com.mipt.rezchikovsergey.sem2.spring_mvp.model.entity.Task;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/** Интерфейс репозитория для хранения и доступа к данным задач. */
public interface TaskRepository {
  void save(Task task);

  List<Task> findAll();

  Optional<Task> findById(UUID id);

  void removeById(UUID id);
}
