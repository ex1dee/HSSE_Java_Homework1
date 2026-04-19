package com.mipt.rezchikovsergey.sem2.spring_mvp.common.model.dto.response;

import com.mipt.rezchikovsergey.sem2.spring_mvp.common.model.enums.TaskPriority;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;
import lombok.Builder;

@Builder
@Schema(description = "Information about a task")
public record TaskResponseDto(
    @Schema(
            description = "Unique identifier of the task",
            example = "550e8400-e29b-41d4-a716-446655440000")
        UUID id,
    @Schema(description = "Task title", example = "Finish homework") String title,
    @Schema(
            description = "Detailed description of the task",
            example = "Complete part 5 of the Spring MVP project")
        String description,
    @Schema(description = "Task creation timestamp", example = "2026-03-24T10:00:00")
        LocalDateTime createdAt,
    @Schema(description = "Task deadline date", example = "2026-12-31") LocalDate dueDate,
    @Schema(description = "Task priority level", example = "HIGH") TaskPriority priority,
    @Schema(
            description = "Set of tags associated with the task",
            example = "[\"study\", \"backend\"]")
        Set<String> tags,
    @Schema(description = "Task completion status", example = "false") boolean completed) {}
