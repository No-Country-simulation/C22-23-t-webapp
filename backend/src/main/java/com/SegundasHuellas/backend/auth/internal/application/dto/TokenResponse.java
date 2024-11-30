package com.SegundasHuellas.backend.auth.internal.application.dto;

public record TokenResponse(
        String token,
        String refreshToken
) {
}
