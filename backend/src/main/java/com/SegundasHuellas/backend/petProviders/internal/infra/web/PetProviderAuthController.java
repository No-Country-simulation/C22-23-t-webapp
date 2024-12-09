package com.SegundasHuellas.backend.petProviders.internal.infra.web;


import com.SegundasHuellas.backend.auth.api.dto.AuthenticationResponse;
import com.SegundasHuellas.backend.petProviders.internal.application.dto.PetProviderRegistrationRequest;
import com.SegundasHuellas.backend.petProviders.internal.application.service.PetProviderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth/petProvider")
@RequiredArgsConstructor
public class PetProviderAuthController {

    private final PetProviderService registrationService;

    @PostMapping("/register")
    public AuthenticationResponse registerPetProvider(PetProviderRegistrationRequest request) {
        return registrationService.registerPetProvider(request);
    }
}
