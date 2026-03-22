package com.mipt.rezchikovsergey.sem2.spring_mvp.repository.impl;

import com.mipt.rezchikovsergey.sem2.spring_mvp.model.entity.BaseEntity;
import com.mipt.rezchikovsergey.sem2.spring_mvp.repository.CrudRepository;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public abstract class InMemoryRepository<T extends BaseEntity> implements CrudRepository<T> {
  protected final ConcurrentMap<UUID, T> data = new ConcurrentHashMap<>();

  @Override
  public void save(T entity) {
    Objects.requireNonNull(entity, "Entity must not be null");

    if (entity.getId() == null) {
      entity.setId(UUID.randomUUID());
    }

    data.put(entity.getId(), entity);
  }

  @Override
  public List<T> findAll() {
    return data.values().stream().toList();
  }

  @Override
  public Optional<T> findById(UUID id) {
    return Optional.ofNullable(data.get(id));
  }

  @Override
  public void removeById(UUID id) {
    data.remove(id);
  }

  @Override
  public boolean existsById(UUID id) {
    return data.containsKey(id);
  }
}
