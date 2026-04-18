package com.mipt.rezchikovsergey.sem2.spring_mvp.external.repository;

import com.mipt.rezchikovsergey.sem2.spring_mvp.common.model.enums.TaskPriority;
import com.mipt.rezchikovsergey.sem2.spring_mvp.external.model.entity.Task;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaTaskRepository extends JpaRepository<Task, UUID> {
  @Query(
      value =
          """
                            SELECT t FROM Task t WHERE t.dueDate BETWEEN :firstDate AND :secondDate
                            """)
  List<Task> findTasksDueBetween(
      @Param("firstDate") LocalDate firstDate, @Param("secondDate") LocalDate secondDate);

  @EntityGraph(attributePaths = {"attachments"})
  @Query("SELECT t FROM Task t")
  List<Task> findAllWithAttachments();

  Optional<Task> findByCompletedAndPriority(boolean completed, TaskPriority priority);
}
