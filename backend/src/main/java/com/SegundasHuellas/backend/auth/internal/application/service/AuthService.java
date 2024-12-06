package com.SegundasHuellas.backend.auth.internal.application.service;

import com.SegundasHuellas.backend.auth.api.RegistrationService;
import com.SegundasHuellas.backend.auth.api.dto.AuthRegistrationRequest;
import com.SegundasHuellas.backend.auth.api.dto.AuthenticationResponse;
import com.SegundasHuellas.backend.auth.api.dto.TokenResponse;
import com.SegundasHuellas.backend.auth.api.dto.UserDetailsResponse;
import com.SegundasHuellas.backend.auth.api.enums.UserRole;
import com.SegundasHuellas.backend.auth.api.events.UserAccountLockedEvent;
import com.SegundasHuellas.backend.auth.api.events.UserLoggedInEvent;
import com.SegundasHuellas.backend.auth.internal.application.dto.LoginRequest;
import com.SegundasHuellas.backend.auth.internal.domain.entity.Token;
import com.SegundasHuellas.backend.auth.internal.domain.entity.User;
import com.SegundasHuellas.backend.auth.internal.infra.persistence.TokenRepository;
import com.SegundasHuellas.backend.auth.internal.infra.persistence.UserRepository;
import com.SegundasHuellas.backend.shared.exception.DomainException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

import static com.SegundasHuellas.backend.auth.internal.application.exceptions.AuthErrorCode.*;
import static com.SegundasHuellas.backend.shared.exception.DomainException.ErrorCode.*;


@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class AuthService implements RegistrationService {

    public static final String BEARER_PREFIX = "Bearer ";
    public static final int BEARER_PREFIX_LENGTH = 7;
    private final ApplicationEventPublisher eventPublisher;
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;


    public AuthenticationResponse register(AuthRegistrationRequest request, Long domainUserId) {
        validateRegistration(request);

        User user = User.builder()
                        .email(request.email())
                        .password(passwordEncoder.encode(request.password()))
                        .lastLoginDate(LocalDateTime.now()) // First login when registering
                        .roles(Set.of(UserRole.USER, request.role()))
                        .domainUserId(domainUserId)
                        .build();

        user = userRepository.save(user);

        TokenResponse tokens = generateTokens(user);

        return buildAuthResponse(user, tokens);
    }

    @Override
    public UserDetailsResponse getUserDetails(Long userId) {

        UserDetailsResponse details = userRepository.findUserDetails(userId)
                                                    .orElseThrow(() -> new DomainException(RESOURCE_NOT_FOUND, userId.toString()));

        Set<UserRole> roles = userRepository.findUserRoles(userId);

        return details.withRoles(roles);
    }

    @Transactional(noRollbackFor = {DomainException.class})
    public AuthenticationResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.email())
                                  .orElseThrow(() -> new DomainException(INVALID_CREDENTIALS));
        if (user.isLocked()) {
            throw new DomainException(ACCOUNT_LOCKED);
        }

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.email(),
                            request.password())
            );

            handleSuccessfulLogin(user);
            TokenResponse tokens = generateTokens(user);
            return buildAuthResponse(user, tokens);


        } catch (AuthenticationException e) {
            handleFailedLogin(user);

            log.warn("Login failed for user [{}]: {}", request.email(), e.getClass());
            throw new DomainException(INVALID_CREDENTIALS);
        }
    }

    public TokenResponse refreshToken(String authHeader) {

        if (authHeader == null || !authHeader.startsWith(BEARER_PREFIX)) {
            throw new DomainException(INVALID_REFRESH_TOKEN);
        }
        String refreshToken = authHeader.substring(BEARER_PREFIX_LENGTH);
        String userEmail = jwtService.extractUsername(refreshToken);

        if (userEmail == null) {
            throw new DomainException(INVALID_REFRESH_TOKEN);
        }

        User user = userRepository.findByEmail(userEmail)
                                  .orElseThrow(() -> new DomainException(INVALID_CREDENTIALS));


        if (!jwtService.isTokenValid(refreshToken, user)) {
            throw new DomainException(INVALID_REFRESH_TOKEN);
        }

        revokeAllUserTokens(user);
        TokenResponse tokenResponse = generateTokens(user);
        log.info("🔐 Refreshed token for user: {}", user.getEmail());
        return tokenResponse;
    }

    private void validateRegistration(AuthRegistrationRequest request) {
        if (userRepository.existsByEmail(request.email())) {
            throw new DomainException(DUPLICATED_DATA, "Email already registered");
        }

        if (request.role() == UserRole.ADMIN) {
            throw new DomainException(INVALID_DATA, "Cannot register with ADMIN role");
        }
    }

    private void handleSuccessfulLogin(User user) {
        user.recordSuccessfulLogin();
        revokeAllUserTokens(user);
        eventPublisher.publishEvent(new UserLoggedInEvent(user.getId(), user.getEmail()));
    }

    private void handleFailedLogin(User user) {
        user.incrementFailedAttemptsCount();

        if (user.isLocked()) {
            eventPublisher.publishEvent(new UserAccountLockedEvent(user.getId(), user.getEmail()));
        }
    }


    private TokenResponse generateTokens(User user) {
        String accessToken = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);
        saveUserToken(user, accessToken);

        return new TokenResponse(accessToken, refreshToken, jwtService.extractExpiration(accessToken));
    }

    private void revokeAllUserTokens(User user) {
        tokenRepository.revokeAllUserTokens(user.getId());
    }

    private void saveUserToken(User savedUser, String jwtToken) {
        Token token = Token.builder()
                           .user(savedUser) // bad here modulith?
                           .token(jwtToken)
                           .tokenType(Token.TokenType.BEARER)
                           .expired(false)
                           .revoked(false)
                           .build();

        tokenRepository.save(token);
    }

    private AuthenticationResponse buildAuthResponse(User user, TokenResponse tokens) {
        return new AuthenticationResponse(
                user.getId(),
                user.getEmail(),
                user.getRoles().stream().map(UserRole::getAuthority).collect(Collectors.toSet()),
                tokens
        );
    }

}



