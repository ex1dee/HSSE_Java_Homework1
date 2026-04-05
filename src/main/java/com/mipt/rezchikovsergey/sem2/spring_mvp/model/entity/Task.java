package com.mipt.rezchikovsergey.sem2.spring_mvp.model.entity;

import com.mipt.rezchikovsergey.sem2.spring_mvp.model.enums.TaskPriority;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

/** Сущность, представляющая задачу. */
@Data
@Entity
@Table(name = "tasks")
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class Task extends BaseEntity {
  private String title;
  private String description;

  @Column(name = "due_date")
  private LocalDate dueDate;

  @Enumerated(EnumType.STRING)
  private TaskPriority priority;

  @JdbcTypeCode(SqlTypes.JSON)
  @Builder.Default
  private Set<String> tags = Set.of();

  @Builder.Default private boolean completed = false;

  @OneToMany(
      mappedBy = "task",
      cascade = CascadeType.ALL,
      orphanRemoval = true,
      fetch = FetchType.LAZY)
  @Builder.Default
  @EqualsAndHashCode.Exclude
  @ToString.Exclude
  private List<TaskAttachment> attachments = new ArrayList<>();
}
