package com.SegundasHuellas.backend.auth.api.events;

public record UserLoggedInEvent(
        Long userId,
        String userEmail
) {
}
