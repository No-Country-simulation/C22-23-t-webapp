package com.SegundasHuellas.backend.auth.internal.application.service;

import com.SegundasHuellas.backend.auth.internal.domain.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;

@Service
public class JwtService {

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
        return getTokenClaims(token).getSubject();
    }

    public boolean isTokenValid(String token, User user) {
        String userEmail = extractUsername(token);
        return (userEmail.equals(user.getEmail()) && !isTokenExpired(token));
    }

    private Claims getTokenClaims(String token) {
        return Jwts.parser().
                   verifyWith(getSignInKey())
                   .build()
                   .parseSignedClaims(token)
                   .getPayload();
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return getTokenClaims(token)
                .getExpiration();
    }

    private String buildToken(User user, Long expiration) {
        return Jwts.builder()
                   .id(user.getId().toString())
                   .claims(Map.of("email", user.getEmail()))
                   .subject(user.getEmail())
                   .issuedAt(new Date(System.currentTimeMillis()))
                   .expiration(new Date(System.currentTimeMillis() + expiration))
                   .signWith(getSignInKey())
                   .compact();

    }

    private SecretKey getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

}
