package com.SegundasHuellas.backend.auth.internal.application.service;

import com.SegundasHuellas.backend.auth.api.SecurityPort;
import com.SegundasHuellas.backend.auth.internal.application.config.SecurityUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SpringSecurityAdapter implements SecurityPort {
    @Override
    public Long getCurrentUserId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return ((SecurityUser) auth.getPrincipal()).getUser().getDomainUserId();

    }
}
