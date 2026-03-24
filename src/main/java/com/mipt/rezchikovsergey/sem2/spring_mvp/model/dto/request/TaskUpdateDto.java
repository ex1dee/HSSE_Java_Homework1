package com.mipt.rezchikovsergey.sem2.spring_mvp.model.dto.request;

import com.mipt.rezchikovsergey.sem2.spring_mvp.model.dto.groups.OnUpdate;
import com.mipt.rezchikovsergey.sem2.spring_mvp.model.enums.TaskPriority;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Set;
import lombok.Builder;

@Builder
@Schema(description = "Data transfer object for updating an existing task")
public record TaskUpdateDto(
    @Schema(
            description = "New task title (3-100 characters)",
            example = "Updated task title",
            nullable = true)
        @Size(min = 3, max = 100, groups = OnUpdate.class)
        String title,
    @Schema(
            description = "New task description",
            example = "Updated description text",
            nullable = true)
        @Size(max = 500, groups = OnUpdate.class)
        String description,
    @Schema(description = "Updated completion status", example = "true", nullable = true)
        Boolean completed,
    @Schema(description = "New deadline date", example = "2026-05-20", nullable = true)
        @FutureOrPresent(groups = OnUpdate.class)
        LocalDate dueDate,
    @Schema(description = "New priority level", example = "LOW", nullable = true)
        TaskPriority priority,
    @Schema(description = "New set of tags (max 5)", example = "[\"urgent\"]", nullable = true)
        @Size(max = 5, groups = OnUpdate.class)
        Set<String> tags) {}
