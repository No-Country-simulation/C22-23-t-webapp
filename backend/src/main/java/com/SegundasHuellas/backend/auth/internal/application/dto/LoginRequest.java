package com.SegundasHuellas.backend.auth.internal.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Schema(description = "Login request payload")
public record LoginRequest(

        @Schema(
                description = "User email address",
                example = "user@example.com",
                pattern = "^[A-Za-z0-9+_.-]+@(.+)$"
        )
        @NotNull
        @Email
        String email,

        @Schema(
                description = "User password that must be at least 8 characters long and contain at least: " +
                              "one digit (0-9), " +
                              "one lowercase letter (a-z), " +
                              "one uppercase letter (A-Z), " +
                              "one special character (@#$%^&+=!). " +
                              "Whitespace is not allowed.",
                example = "P@ssw0rd123",
                minLength = 8
        )
        @NotNull
        @Size(min = 8)
        String password
) {

    public LoginRequest {
        email = email != null ? email.toLowerCase() : null;
    }
}
