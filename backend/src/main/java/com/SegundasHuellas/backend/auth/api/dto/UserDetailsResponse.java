package com.SegundasHuellas.backend.auth.api.dto;

import com.SegundasHuellas.backend.auth.api.enums.UserRole;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

public record UserDetailsResponse(
        String email,
        Set<UserRole> roles,
        LocalDateTime lastLoginDate,
        boolean active,
        boolean locked
) {

    public UserDetailsResponse(String email, LocalDateTime lastLoginDate, boolean active, boolean locked) {
        this(email, new HashSet<>(), lastLoginDate, active, locked);
    }

    public UserDetailsResponse withRoles(Set<UserRole> roles) {
        return new UserDetailsResponse(email, roles, lastLoginDate, active, locked);
    }
}
