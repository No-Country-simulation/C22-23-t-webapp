package com.SegundasHuellas.backend.adopters.internal.application.dto;

import com.SegundasHuellas.backend.auth.api.dto.AuthRegistrationRequest;
import com.SegundasHuellas.backend.auth.api.enums.UserRole;
import com.SegundasHuellas.backend.shared.application.validation.StrongPassword;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record AdopterRegistrationRequest(

        @NotNull
        String firstName,

        @NotNull
        String lastName,

        @NotNull
        @Email
        String email,

        @NotNull
        @StrongPassword
        String password,

        @NotNull
        String passwordConfirmation


) {

    public AuthRegistrationRequest toAuthRequest() {
        return new AuthRegistrationRequest(
                email,
                password,
                UserRole.ADOPTER
        );
    }

    @AssertTrue(message = "Passwords do not match")
    private boolean passwordsMatch() {
        return password != null && password.equals(passwordConfirmation);
    }
}
