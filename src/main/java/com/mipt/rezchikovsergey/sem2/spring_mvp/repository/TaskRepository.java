package com.mipt.rezchikovsergey.sem2.spring_mvp.repository;

import com.mipt.rezchikovsergey.sem2.spring_mvp.model.dto.TaskPriorityCountDto;
import com.mipt.rezchikovsergey.sem2.spring_mvp.model.entity.Task;
import com.mipt.rezchikovsergey.sem2.spring_mvp.model.enums.TaskPriority;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
public class TaskRepository implements CrudRepository<Task> {
  private final JpaTaskRepository jpaRepository;
  private final JdbcTemplate jdbcTemplate;

  @Override
  @Transactional
  public void save(Task entity) {
    jpaRepository.save(entity);
  }

  @Override
  @Transactional(readOnly = true)
  public List<Task> findAll() {
    return jpaRepository.findAll();
  }

  @Override
  @Transactional(readOnly = true)
  public Optional<Task> findById(UUID id) {
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
  public Optional<Task> findByCompletedAndPriority(boolean completed, TaskPriority priority) {
    return jpaRepository.findByCompletedAndPriority(completed, priority);
  }

  @Transactional(readOnly = true)
  public List<Task> findTasksDueInNextWeek() {
    LocalDate now = LocalDate.now();
    return jpaRepository.findTasksDueBetween(now, now.plusDays(7));
  }

  @Transactional(readOnly = true)
  public List<Task> findAllWithAttachments() {
    return jpaRepository.findAllWithAttachments();
  }

  @Transactional(readOnly = true)
  public List<TaskPriorityCountDto> getTasksCountByPriority() {
    String sql = "SELECT priority, COUNT(*) as task_count FROM tasks GROUP BY priority";

    return jdbcTemplate.query(
        sql,
        (rs, rowNum) ->
            new TaskPriorityCountDto(
                TaskPriority.valueOf(rs.getString("priority")), rs.getLong("task_count")));
  }
}
