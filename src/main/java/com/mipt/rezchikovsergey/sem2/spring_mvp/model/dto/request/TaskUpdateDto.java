package com.mipt.rezchikovsergey.sem2.spring_mvp.model.dto.request;

import com.mipt.rezchikovsergey.sem2.spring_mvp.model.enums.TaskPriority;
import java.time.LocalDate;
import java.util.Set;
import lombok.Builder;

@Builder
public record TaskUpdateDto(
    String title,
    String description,
    Boolean completed,
    LocalDate dueDate,
    TaskPriority priority,
    Set<String> tags) {}
