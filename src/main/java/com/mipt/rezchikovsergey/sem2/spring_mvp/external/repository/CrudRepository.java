package com.mipt.rezchikovsergey.sem2.spring_mvp.external.repository;

import com.mipt.rezchikovsergey.sem2.spring_mvp.external.model.entity.BaseEntity;
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
