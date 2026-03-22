package com.mipt.rezchikovsergey.sem2.spring_mvp.model.dto.request;


public record UpdateTaskRequest(String title, String description, Boolean completed) {}
