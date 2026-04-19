package com.mipt.rezchikovsergey.sem2.spring_mvp.security.jwt;

import java.util.List;
import java.util.UUID;

public record JwtPayload(String username, UUID userId, List<String> roles) {}
