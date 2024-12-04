package com.SegundasHuellas.backend.auth.api.dto;

import com.SegundasHuellas.backend.auth.api.enums.UserRole;

public record AuthRegistrationRequest(

        String email,
        String password,
        UserRole role
) {
    public AuthRegistrationRequest {
        email = email != null ? email.toLowerCase().trim() : null;
    }
}


//public record PetProviderRegistrationRequest(
//
//        @Email
//        @NotNull
//        String email,
//
//        @StrongPassword
//        String password,
//
//        @NotNull
//        String passwordConfirmation
//
//) {
//    public AuthRegistrationRequest {
//        email = email != null ? email.toLowerCase().trim() : null;
//    }
//
//    @AssertTrue(message = "Passwords do not match")
//    private boolean passwordsMatch() {
//        return password != null && password.equals(passwordConfirmation);
//    }
//}