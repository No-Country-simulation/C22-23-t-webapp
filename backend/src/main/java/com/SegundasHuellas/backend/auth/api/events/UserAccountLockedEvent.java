package com.SegundasHuellas.backend.auth.api.events;

public record UserAccountLockedEvent(
        Long userId,
        String userEmail
) {
}
