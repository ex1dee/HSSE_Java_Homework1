package com.mipt.rezchikovsergey.sem2.spring_mvp.common.model.dto;

import com.mipt.rezchikovsergey.sem2.spring_mvp.common.model.enums.TaskPriority;

public record TaskPriorityCountDto(TaskPriority priority, long count) {}
