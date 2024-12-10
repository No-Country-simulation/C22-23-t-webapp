package com.SegundasHuellas.backend.auth.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.time.Instant;

@Schema(description = "JWT token response containing authentication and refresh tokens")
public record TokenResponse(

        @Schema(
                description = "JWT access token for API authentication",
                example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        @NotNull
        String token,

        @NotNull
        @Schema(
                description = "JWT refresh token for obtaining new access tokens",
                example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        @NotNull
        String refreshToken,


        @Schema(
                description = "Timestamp indicating when the access token will expire",
                example = "2024-03-15T10:30:00Z",
                requiredMode = Schema.RequiredMode.REQUIRED,
                type = "string",
                format = "date-time"
        )
        @NotNull
        Instant expiresAt
) {

}
