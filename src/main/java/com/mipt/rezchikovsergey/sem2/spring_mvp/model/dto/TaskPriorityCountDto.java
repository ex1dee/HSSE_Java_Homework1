package com.mipt.rezchikovsergey.sem2.spring_mvp.model.dto;

import com.mipt.rezchikovsergey.sem2.spring_mvp.model.enums.TaskPriority;

public record TaskPriorityCountDto(TaskPriority priority, long count) {}
