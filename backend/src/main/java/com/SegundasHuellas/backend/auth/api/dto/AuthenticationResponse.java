package com.SegundasHuellas.backend.auth.api.dto;

import java.util.Set;

public record AuthenticationResponse(
        Long userId,
        String email,
        Set<String> roles,
        TokenResponse tokens
) {

}
