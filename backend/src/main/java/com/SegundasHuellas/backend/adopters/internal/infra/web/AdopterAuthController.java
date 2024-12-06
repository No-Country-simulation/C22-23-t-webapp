package com.SegundasHuellas.backend.adopters.internal.infra.web;

import com.SegundasHuellas.backend.adopters.internal.application.dto.AdopterDetailsResponse;
import com.SegundasHuellas.backend.adopters.internal.application.dto.AdopterRegistrationRequest;
import com.SegundasHuellas.backend.adopters.internal.application.service.AdopterRegistrationService;
import com.SegundasHuellas.backend.auth.api.dto.AuthenticationResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth/adopters") // Agregamos "auth" para que no sea bloqueado por la config de seguridad
@RequiredArgsConstructor
public class AdopterAuthController {


    private final AdopterRegistrationService registrationService;

    @PostMapping("/register")
    public AuthenticationResponse register(@RequestBody @Valid AdopterRegistrationRequest request) {
        return registrationService.register(request);
    }

    @GetMapping("/{userId}")
    public AdopterDetailsResponse getAdopterDetails(@PathVariable(name = "userId") Long userId) {
        return registrationService.getAdopterDetails(userId);
    }

}