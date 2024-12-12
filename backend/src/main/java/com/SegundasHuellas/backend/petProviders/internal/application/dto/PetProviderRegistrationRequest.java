package com.SegundasHuellas.backend.petProviders.internal.application.dto;

import com.SegundasHuellas.backend.auth.api.dto.AuthRegistrationRequest;
import com.SegundasHuellas.backend.petProviders.internal.domain.enums.PetProviderType;
import com.SegundasHuellas.backend.shared.application.validation.StrongPassword;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import com.SegundasHuellas.backend.auth.api.enums.UserRole;

public record PetProviderRegistrationRequest(
        @NotNull
        String name,

        @NotNull
        @Email
        String email,

        @NotNull
        @StrongPassword
        String password,

        @NotNull
        String passwordConfirmation,

        PetProviderType type
) {
    public AuthRegistrationRequest toAuthRequest() {
        return new AuthRegistrationRequest(email, password, UserRole.PROVIDER);
    }

    @AssertTrue(message = "Passwords do not match")
    private boolean passwordsMatch() {
        return password == null || password.equals(passwordConfirmation);
    }
}
