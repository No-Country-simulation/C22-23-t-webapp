package com.SegundasHuellas.backend.auth.internal.application.dto;

public record LoginRequest(
        String email,
        String password
) {
}
