package com.mipt.rezchikovsergey.sem2.spring_mvp.model.dto.request;

import com.mipt.rezchikovsergey.sem2.spring_mvp.model.dto.groups.OnUpdate;
import com.mipt.rezchikovsergey.sem2.spring_mvp.model.enums.TaskPriority;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Set;
import lombok.Builder;

@Builder
public record TaskUpdateDto(
    @Size(min = 3, max = 100, groups = OnUpdate.class) String title,
    @Size(max = 500, groups = OnUpdate.class) String description,
    Boolean completed,
    @FutureOrPresent(groups = OnUpdate.class) LocalDate dueDate,
    TaskPriority priority,
    @Size(max = 5, groups = OnUpdate.class) Set<String> tags) {}
