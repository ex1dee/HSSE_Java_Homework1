package com.mipt.rezchikovsergey.sem2.spring_mvp.model.dto.response;

import java.util.List;

public record ProfileResponseDto(String username, List<String> roles, List<String> permissions) {}
