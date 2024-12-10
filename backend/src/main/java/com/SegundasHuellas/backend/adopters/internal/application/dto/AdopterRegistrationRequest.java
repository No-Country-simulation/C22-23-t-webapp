package com.SegundasHuellas.backend.adopters.internal.application.dto;

import com.SegundasHuellas.backend.auth.api.dto.AuthRegistrationRequest;
import com.SegundasHuellas.backend.auth.api.enums.UserRole;
import com.SegundasHuellas.backend.shared.application.validation.StrongPassword;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
@Schema(description = "Request payload for adopter registration")
public record AdopterRegistrationRequest(

        @Schema(
                description = "Adopter's first name",
                example = "John",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        @NotNull
        String firstName,

        @NotNull
        @Schema(
                description = "Adopter's last name",
                example = "Doe",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        @NotNull
        String lastName,


        @Schema(
                description = "Adopter's email address (will be used for login)",
                example = "john.doe@example.com",
                requiredMode = Schema.RequiredMode.REQUIRED,
                pattern = "^[A-Za-z0-9+_.-]+@(.+)$"
        )
        @NotNull
        @Email
        String email,

        @Schema(
                description = "Password must be at least 8 characters long and contain at least: " +
                              "one digit (0-9), " +
                              "one lowercase letter (a-z), " +
                              "one uppercase letter (A-Z), " +
                              "one special character (@#$%^&+=!). " +
                              "Whitespace is not allowed.",
                example = "P@ssw0rd123",
                requiredMode = Schema.RequiredMode.REQUIRED,
                minLength = 8
        )
        @NotNull
        @StrongPassword
        String password,

        @Schema(
                description = "Confirmation of the password (must match password field)",
                example = "P@ssw0rd123",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
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
