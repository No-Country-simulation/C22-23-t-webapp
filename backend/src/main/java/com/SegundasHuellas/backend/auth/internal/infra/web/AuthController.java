package com.SegundasHuellas.backend.auth.internal.infra.web;

import com.SegundasHuellas.backend.auth.internal.application.dto.AuthenticationResponse;
import com.SegundasHuellas.backend.auth.internal.application.dto.LoginRequest;
import com.SegundasHuellas.backend.auth.internal.application.dto.RegistrationRequest;
import com.SegundasHuellas.backend.auth.internal.application.dto.TokenResponse;
import com.SegundasHuellas.backend.auth.internal.application.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public AuthenticationResponse register(@RequestBody @Valid RegistrationRequest registrationRequest) {
        return authService.register(registrationRequest);
    }

    @PostMapping("/login")
    public AuthenticationResponse login(@RequestBody @Valid LoginRequest loginRequest) {
        return authService.login(loginRequest);
    }

    @PostMapping("/refresh")
    public TokenResponse refreshToken(@RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader) {
        return authService.refreshToken(authHeader);
    }

}
