package com.mipt.rezchikovsergey.sem2.spring_mvp.repository;

import com.mipt.rezchikovsergey.sem2.spring_mvp.model.entity.BaseEntity;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CrudRepository<T extends BaseEntity> {
  void save(T entity);

  List<T> findAll();

  Optional<T> findById(UUID id);

  void removeById(UUID id);

  boolean existsById(UUID id);
}
