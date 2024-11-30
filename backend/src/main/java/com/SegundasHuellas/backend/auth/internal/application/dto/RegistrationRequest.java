package com.SegundasHuellas.backend.auth.internal.application.dto;

public record RegistrationRequest(
        String email,
        String password,
        String passwordConfirmation
) {
}
