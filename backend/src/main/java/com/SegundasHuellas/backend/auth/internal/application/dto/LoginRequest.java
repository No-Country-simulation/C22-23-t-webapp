package com.SegundasHuellas.backend.auth.internal.application.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record LoginRequest(

        @NotNull
        @Email
        String email,

        @NotNull
        @Size(min = 8)
        String password
) {

    public LoginRequest {
        email = email != null ? email.toLowerCase() : null;
    }
}
