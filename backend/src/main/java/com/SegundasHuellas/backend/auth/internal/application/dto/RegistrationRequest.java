package com.SegundasHuellas.backend.auth.internal.application.dto;

import com.SegundasHuellas.backend.shared.application.validation.StrongPassword;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record RegistrationRequest(
        @NotNull
        @Email
        String email,

        @NotNull
        @StrongPassword
        String password,

        @NotNull
        String passwordConfirmation
) {
    public RegistrationRequest {
        email = email != null ? email.toLowerCase().trim() : null;
    }

    @AssertTrue(message = "Passwords do not match")
    private boolean passwordsMatch() {
        return password != null && password.equals(passwordConfirmation);
    }

}
