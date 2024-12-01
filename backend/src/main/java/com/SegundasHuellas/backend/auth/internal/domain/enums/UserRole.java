package com.SegundasHuellas.backend.auth.internal.domain.enums;

import org.springframework.security.core.GrantedAuthority;


public enum UserRole implements GrantedAuthority {
    ADMIN,
    USER,
    ADOPTER,
    PROVIDER;

    @Override
    public String getAuthority() {
        return name();
    }
}
