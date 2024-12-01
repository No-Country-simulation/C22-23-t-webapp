package com.SegundasHuellas.backend.auth.internal.application.dto;

import com.SegundasHuellas.backend.auth.internal.domain.entity.Token;
import jakarta.validation.constraints.NotNull;

import java.time.Instant;

public record TokenResponse(
        @NotNull
        String token,
        @NotNull
        String refreshToken,

        @NotNull
        Instant expiresAt,

        @NotNull
        Token.TokenType tokenType

) {
    public static TokenResponse bearer(String token, String refreshToken, Instant expiresAt) {
        return new TokenResponse(token, refreshToken, expiresAt, Token.TokenType.BEARER);
    }
}
