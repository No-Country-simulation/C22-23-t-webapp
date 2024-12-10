package com.SegundasHuellas.backend.auth.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Set;

@Schema(description = "Response payload after successful authentication")
public record AuthenticationResponse(
        @Schema(
                description = "Unique identifier of the authenticated user",
                example = "1234"
        )
        Long userId,

        @Schema(
                description = "Email address of the authenticated user",
                example = "user@example.com"
        )
        String email,

        @Schema(
                description = "Set of roles assigned to the user",
                example = "[\"USER\", \"ADOPTER\"]"
        )
        Set<String> roles,

        @Schema(
                description = "JWT tokens for authentication and refresh"
        )
        TokenResponse tokens
) {

}
