package com.SegundasHuellas.backend.auth.api.dto;

import jakarta.validation.constraints.NotNull;

import java.time.Instant;

public record TokenResponse(
        @NotNull
        String token,
        @NotNull
        String refreshToken,

        @NotNull
        Instant expiresAt
) {

}
