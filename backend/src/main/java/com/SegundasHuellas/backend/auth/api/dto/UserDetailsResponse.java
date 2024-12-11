package com.SegundasHuellas.backend.auth.api.dto;

import com.SegundasHuellas.backend.auth.api.enums.UserRole;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Schema(description = "Response containing user details and status information")
public record UserDetailsResponse(
        @Schema(
                description = "User's email address",
                example = "user@example.com"
        )
        String email,

        @Schema(
                description = "Set of roles assigned to the user",
                example = "[\"ADOPTER\", \"USER\"]"
        )
        Set<UserRole> roles,

        @Schema(
                description = "Timestamp of user's last login",
                example = "2024-03-15T10:30:00",
                type = "string",
                format = "date-time"
        )
        LocalDateTime lastLoginDate,

        @Schema(
                description = "Indicates if the user account is active",
                example = "true"
        )
        boolean active,

        @Schema(
                description = "Indicates if the user account is locked due to security reasons",
                example = "false"
        )
        boolean locked
) {

    public UserDetailsResponse(String email, LocalDateTime lastLoginDate, boolean active, boolean locked) {
        this(email, new HashSet<>(), lastLoginDate, active, locked);
    }

    public UserDetailsResponse withRoles(Set<UserRole> roles) {
        return new UserDetailsResponse(email, roles, lastLoginDate, active, locked);
    }
}
