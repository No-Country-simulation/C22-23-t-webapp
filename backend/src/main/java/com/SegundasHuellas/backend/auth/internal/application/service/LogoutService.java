package com.SegundasHuellas.backend.auth.internal.application.service;

import com.SegundasHuellas.backend.auth.internal.domain.entity.Token;
import com.SegundasHuellas.backend.auth.internal.infra.persistence.TokenRepository;
import com.SegundasHuellas.backend.shared.exception.DomainException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.SegundasHuellas.backend.auth.internal.application.exceptions.AuthErrorCode.INVALID_TOKEN;
import static com.SegundasHuellas.backend.shared.exception.DomainException.ErrorCode.RESOURCE_NOT_FOUND;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class LogoutService implements LogoutHandler {

    private final TokenRepository tokenRepository;

    public void configure(LogoutConfigurer<HttpSecurity> logout) {
        logout
                .logoutUrl("/auth/logout")
                .addLogoutHandler(this)
                .logoutSuccessHandler((req, res, auth) -> SecurityContextHolder.clearContext());
    }

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        try {
            validateAndRevokeToken(authHeader);
        } catch (IllegalArgumentException e) {
            throw new DomainException(INVALID_TOKEN);
        }
    }

    private void validateAndRevokeToken(String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new IllegalArgumentException("Invalid Authorization header format");
        }

        String jwtToken = authHeader.substring(7);
        Token storedToken = tokenRepository.findByToken(jwtToken)
                                           .orElseThrow(() -> new DomainException(RESOURCE_NOT_FOUND));

        revokeToken(storedToken);
        tokenRepository.save(storedToken);

//        tokenRepository.deleteExpiredTokensForUser(storedToken.getUser().getId());
//        log.debug("Token revoked and expired tokens cleaned up for user: {}",
//                storedToken.getUser().getId());
    }

    private void revokeToken(Token token) {
        token.setRevoked(true);
        token.setExpired(true);
    }
}
