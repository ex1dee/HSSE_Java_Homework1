package com.mipt.rezchikovsergey.sem2.spring_mvp.model.dto.response;

import com.mipt.rezchikovsergey.sem2.spring_mvp.model.enums.TaskPriority;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;
import lombok.Builder;

@Builder
public record TaskResponseDto(
    UUID id,
    String title,
    String description,
    LocalDateTime createdAt,
    LocalDate dueDate,
    TaskPriority priority,
    Set<String> tags,
    boolean completed) {}
