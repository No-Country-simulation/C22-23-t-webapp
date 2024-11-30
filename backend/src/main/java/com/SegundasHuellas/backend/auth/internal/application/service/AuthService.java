package com.SegundasHuellas.backend.auth.internal.application.service;

import com.SegundasHuellas.backend.auth.internal.application.dto.LoginRequest;
import com.SegundasHuellas.backend.auth.internal.application.dto.RegistrationRequest;
import com.SegundasHuellas.backend.auth.internal.application.dto.TokenResponse;
import com.SegundasHuellas.backend.auth.internal.domain.entity.Token;
import com.SegundasHuellas.backend.auth.internal.domain.entity.User;
import com.SegundasHuellas.backend.auth.internal.infra.persistence.TokenRepository;
import com.SegundasHuellas.backend.auth.internal.infra.persistence.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public TokenResponse register(RegistrationRequest request) {


        User user = User.builder()
                        .email(request.email())
                        .password(passwordEncoder.encode(request.password())) //should do checks on the password confirmation
                        .build();

        User savedUser = userRepository.save(user);

        String jwtToken = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);
        saveUserToken(savedUser, jwtToken);
        return new TokenResponse(jwtToken, refreshToken);
    }

    public TokenResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.email(),
                        request.password()

                )
        );

        User user = userRepository.findByEmail(request.email())
                                  .orElseThrow();//TODO: throw exception

        String jwtToken = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);
        revokeAllUserTokens(user); //SOlo 1 cliente por logeado?
        saveUserToken(user, jwtToken);
        return new TokenResponse(jwtToken, refreshToken);

    }

    public TokenResponse refreshToken(String authHeader) {

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new IllegalArgumentException("Invalid Authorization token"); // TODO: USE DOMAIN EXCEPTION
        }
        String refreshToken = authHeader.substring(7);
        String userEmail  = jwtService.extractUsername(refreshToken);

        if (userEmail == null) {
            throw new IllegalArgumentException("Invalid Authorization token"); // TODO: USE DOMAIN EXCEPTION
        }

        User user = userRepository.findByEmail(userEmail)
                                  .orElseThrow();//DOMAIN EXCEPTION or UsernameNotFoundException? tutorial says the second.


        if (!jwtService.isTokenValid(refreshToken, user)) {
            throw new IllegalArgumentException("Invalid Authorization token"); // TODO: USE DOMAIN EXCEPTION??
        }

        String accessToken = jwtService.generateToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, accessToken);

        return new TokenResponse(accessToken, refreshToken);

    }

    private void revokeAllUserTokens(User user) {
        List<Token> validUserTokens = tokenRepository.findAllValidIsFalseOrRevokedIsFalseByUserId(user.getId());

        if (!validUserTokens.isEmpty()) {
            for (Token token : validUserTokens) {
                token.setRevoked(true);
                token.setExpired(true);
            }
            tokenRepository.saveAll(validUserTokens);
        }
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

}
