package com.SegundasHuellas.backend.auth.internal.application.dto;

import com.SegundasHuellas.backend.shared.application.validation.StrongPassword;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record PasswordResetRequest(
        @NotNull
        @Email
        String email,

        @NotNull
        @StrongPassword
        String newPassword,

        @NotNull
        String newPasswordConfirmation
) {

    public PasswordResetRequest {
        email = email != null ? email.toLowerCase().trim() : null;
    }

    @AssertTrue(message = "Passwords do not match")
    private boolean passwordsMatch() {
        return newPassword != null && newPassword.equals(newPasswordConfirmation);
    }
}
