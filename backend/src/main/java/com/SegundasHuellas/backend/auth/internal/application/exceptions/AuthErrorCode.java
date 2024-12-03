package com.SegundasHuellas.backend.auth.internal.application.exceptions;

import com.SegundasHuellas.backend.shared.exception.ErrorCodeProvider;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;

public enum AuthErrorCode implements ErrorCodeProvider {
    TOKEN_EXPIRED(UNAUTHORIZED),
    INVALID_TOKEN(UNAUTHORIZED),
    INVALID_REFRESH_TOKEN(UNAUTHORIZED),
    TOKEN_REVOKED(UNAUTHORIZED),
    INVALID_CREDENTIALS(UNAUTHORIZED),
    ACCOUNT_LOCKED(UNAUTHORIZED),
    ACCOUNT_DISABLED(UNAUTHORIZED);

    private final HttpStatus status;

    AuthErrorCode(HttpStatus status) {
        this.status = status;
    }

    @Override
    public HttpStatus getStatus() {
        return status;
    }
}