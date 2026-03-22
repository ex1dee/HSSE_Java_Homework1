package com.mipt.rezchikovsergey.sem2.spring_mvp.model.dto.request;

import com.mipt.rezchikovsergey.sem2.spring_mvp.model.dto.groups.OnCreate;
import com.mipt.rezchikovsergey.sem2.spring_mvp.model.enums.TaskPriority;
import com.mipt.rezchikovsergey.sem2.spring_mvp.validation.annotations.DueDateNotBeforeCreation;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Set;
import lombok.Builder;

@Builder
@DueDateNotBeforeCreation
public record TaskCreateDto(
    @NotBlank(groups = OnCreate.class) @Size(min = 3, max = 100, groups = OnCreate.class)
        String title,
    @Size(max = 500, groups = OnCreate.class) String description,
    @FutureOrPresent(groups = OnCreate.class) LocalDate dueDate,
    @NotNull(groups = OnCreate.class) TaskPriority priority,
    @Size(max = 5, groups = OnCreate.class) Set<String> tags) {}
