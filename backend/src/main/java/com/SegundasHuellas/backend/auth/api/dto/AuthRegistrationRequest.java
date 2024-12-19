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