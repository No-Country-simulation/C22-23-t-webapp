package com.SegundasHuellas.backend.auth.internal.application.config;

import com.SegundasHuellas.backend.auth.internal.application.service.JwtService;
import com.SegundasHuellas.backend.auth.internal.application.service.UserService;
import com.SegundasHuellas.backend.auth.internal.domain.entity.Token;
import com.SegundasHuellas.backend.auth.internal.domain.entity.User;
import com.SegundasHuellas.backend.auth.internal.infra.persistence.TokenRepository;
import com.SegundasHuellas.backend.auth.internal.infra.persistence.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final String BEARER_PREFIX = "Bearer ";
    private static final int BEARER_PREFIX_LENGTH = 7;

    private final JwtService jwtService;
    private final TokenRepository tokenRepository;
    private final UserRepository userRepository;
    private final UserService userService;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain)
            throws ServletException, IOException {


        try {

            if (request.getRequestURI().startsWith("/auth")) {
                filterChain.doFilter(request, response);
                return;
            }

            String jwtToken = extractToken(request);

            if (jwtToken == null) {
                filterChain.doFilter(request, response);
                return;
            }

            processToken(jwtToken, request);

        } catch (Exception e) {
            log.error("🚫 Authentication error", e);
        }

        filterChain.doFilter(request, response);
    }

    private String extractToken(HttpServletRequest request) {
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authHeader == null || !authHeader.startsWith(BEARER_PREFIX)) {
            return null;
        }
        return authHeader.substring(BEARER_PREFIX_LENGTH);
    }

    private void processToken(String token, HttpServletRequest request) {
        String userEmail = jwtService.extractUsername(token);
        if (userEmail == null || SecurityContextHolder.getContext().getAuthentication() != null) {
            return;
        }

        Token tokenEntity = validateToken(token);
        if (tokenEntity == null) {
            return;
        }

        UserDetails userDetails = userService.loadUserByUsername(userEmail);
        User user = userRepository.findByEmail(userDetails.getUsername())
                                  .orElse(null);

        if (user == null || !jwtService.isTokenValid(token, user)) {
            return;
        }

        authenticateUser(userDetails, request);
    }

    private Token validateToken(String token) {
        Token tokenEntity = tokenRepository.findByToken(token).orElse(null);
        if (tokenEntity == null || tokenEntity.isExpired() || tokenEntity.isRevoked()) {
            return null;
        }
        return tokenEntity;
    }

    private void authenticateUser(UserDetails userDetails, HttpServletRequest request) {
        var authToken = new UsernamePasswordAuthenticationToken(
                userDetails,
                null,
                userDetails.getAuthorities()
        );
        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authToken);
    }

}
