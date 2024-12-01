package com.SegundasHuellas.backend.auth.internal.application.dto;

import com.SegundasHuellas.backend.auth.internal.domain.entity.User;
import com.SegundasHuellas.backend.auth.api.enums.UserRole;

import java.util.Set;
import java.util.stream.Collectors;

public record AuthenticationResponse(
        String userId,
        String email,
        Set<String> roles,
        TokenResponse tokens
) {

    public static AuthenticationResponse from(User user, TokenResponse tokens) {
        return new AuthenticationResponse(
                user.getId().toString(),
                user.getEmail(),
                user.getRoles().stream().map(UserRole::getAuthority).collect(Collectors.toSet()),
                tokens
        );
    }
}
