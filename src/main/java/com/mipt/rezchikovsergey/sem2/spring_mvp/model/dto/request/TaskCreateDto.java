package com.mipt.rezchikovsergey.sem2.spring_mvp.model.dto.request;

import com.mipt.rezchikovsergey.sem2.spring_mvp.model.dto.groups.OnCreate;
import com.mipt.rezchikovsergey.sem2.spring_mvp.model.enums.TaskPriority;
import com.mipt.rezchikovsergey.sem2.spring_mvp.validation.annotations.DueDateNotBeforeCreation;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Set;
import lombok.Builder;

@Builder
@DueDateNotBeforeCreation
@Schema(description = "Data transfer object for creating a new task")
public record TaskCreateDto(
    @Schema(
            description = "Task title (must be between 3 and 100 characters)",
            example = "Buy groceries",
            requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(groups = OnCreate.class)
        @Size(min = 3, max = 100, groups = OnCreate.class)
        String title,
    @Schema(
            description = "Task description (up to 500 characters)",
            example = "Milk, bread, and butter",
            nullable = true)
        @Size(max = 500, groups = OnCreate.class)
        String description,
    @Schema(
            description = "Task deadline (must be today or in the future)",
            example = "2026-04-01",
            nullable = true)
        @FutureOrPresent(groups = OnCreate.class)
        LocalDate dueDate,
    @Schema(
            description = "Priority of the task",
            example = "MEDIUM",
            requiredMode = Schema.RequiredMode.REQUIRED)
        @NotNull(groups = OnCreate.class)
        TaskPriority priority,
    @Schema(
            description = "Set of tags (max 5 tags allowed)",
            example = "[\"home\", \"shopping\"]",
            nullable = true)
        @Size(max = 5, groups = OnCreate.class)
        Set<String> tags) {}
