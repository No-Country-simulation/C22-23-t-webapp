package com.SegundasHuellas.backend.auth.internal.application.config;

import com.SegundasHuellas.backend.auth.internal.domain.entity.User;
import com.SegundasHuellas.backend.auth.internal.domain.enums.UserRole;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collection;

@Getter
@RequiredArgsConstructor
public class SecurityUser implements UserDetails {

    private final User user;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return user.getRoles();
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return user.isActive();
    }

    @Override
    public boolean isAccountNonLocked() {
        return !user.isLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return user.getPasswordExpiryDate() == null ||
               user.getPasswordExpiryDate().isAfter(LocalDateTime.now());
    }

    @Override
    public boolean isEnabled() {
        return user.isEmailVerified();
    }

    public boolean hasRole(UserRole role) {
        return user.getRoles().contains(role);
    }

    public boolean hasAnyRole(UserRole... roles) {
        return Arrays.stream(roles)
                     .anyMatch(this::hasRole);
    }

    public long getId() {
        return user.getId();
    }
}
