package com.SegundasHuellas.backend.auth.internal.application.service;

import com.SegundasHuellas.backend.auth.api.enums.UserRole;
import com.SegundasHuellas.backend.auth.internal.domain.entity.User;
import com.SegundasHuellas.backend.shared.exception.DomainException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.DecodingException;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Date;
import java.util.Map;

import static com.SegundasHuellas.backend.auth.internal.application.exceptions.AuthErrorCode.INVALID_TOKEN;
import static com.SegundasHuellas.backend.auth.internal.application.exceptions.AuthErrorCode.TOKEN_EXPIRED;

@Slf4j
@Service
public class JwtService {

    private static final String ISSUER = "SegundasHuellas";

    @Value("${app.security.jwt.secret-key}")
    private String secretKey;

    @Value("${app.security.jwt.expiration}")
    private Long expiration;

    @Value("${app.security.jwt.refresh-token.expiration}")
    private Long refreshTokenExpiration;


    public String generateToken(User user) {
        return buildToken(user, expiration);
    }

    public String generateRefreshToken(User user) {
        return buildToken(user, refreshTokenExpiration);
    }

    public String extractUsername(String token) {
        try {
            return extractAllClaims(token).getSubject();
        } catch (ExpiredJwtException e) {
            log.debug("Token expired: {}", e.getMessage());
            throw new DomainException(TOKEN_EXPIRED);
        } catch (JwtException e) {
            log.warn("Invalid token: {}", e.getMessage());
            throw new DomainException(INVALID_TOKEN);
        }
    }

    public boolean isTokenValid(String token, User user) {
        try {
            String userEmail = extractUsername(token);
            return (userEmail.equals(user.getEmail()) && !isTokenExpired(token));
        } catch (JwtException e) {
            return false;
        }
    }

    public Instant extractExpiration(String token) {
        return extractAllClaims(token)
                .getExpiration().toInstant();
    }

    private Map<String, Object> buildUserClaims(User user) {
        return Map.of(
                "roles", user.getRoles().stream()
                             .map(UserRole::name)
                             .toList()
        );
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser().
                   verifyWith(getSignInKey())
                   .build()
                   .parseSignedClaims(token)
                   .getPayload();
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).isBefore(Instant.now());
    }

    private String buildToken(User user, Long expiration) {
        Instant now = Instant.now();
        return Jwts.builder()
                   .id(user.getId().toString())
                   .claims(buildUserClaims(user))
                   .subject(user.getEmail())
                   .issuer(ISSUER)
                   .issuedAt(Date.from(now))
                   .expiration(Date.from(now.plusMillis(expiration)))
                   .signWith(getSignInKey())
                   .compact();

    }

    private SecretKey getSignInKey() {
        try {
            byte[] keyBytes = Decoders.BASE64.decode(secretKey);
            return Keys.hmacShaKeyFor(keyBytes);
        } catch (DecodingException e) {
            log.error("Invalid JWT secret key encoding: {}", e.getMessage());
            throw new SecurityException("Invalid JWT secret key configuration");
        }
    }

}
