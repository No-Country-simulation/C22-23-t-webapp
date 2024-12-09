package com.SegundasHuellas.backend.petProviders.internal.infra.web;


import com.SegundasHuellas.backend.auth.api.dto.AuthenticationResponse;
import com.SegundasHuellas.backend.petProviders.internal.application.dto.PetProviderDetailResponse;
import com.SegundasHuellas.backend.petProviders.internal.application.dto.PetProviderRegistrationRequest;
import com.SegundasHuellas.backend.petProviders.internal.application.dto.PetProviderSummaryResponse;
import com.SegundasHuellas.backend.petProviders.internal.application.dto.PetProviderUpdateRequest;
import com.SegundasHuellas.backend.petProviders.internal.application.service.PetProviderService;
import com.SegundasHuellas.backend.shared.application.dto.PageResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/auth/pet-provider")
@RequiredArgsConstructor
public class PetProviderAuthController {

    private final PetProviderService registrationService;

    @PostMapping("/register")
    public AuthenticationResponse registerPetProvider(PetProviderRegistrationRequest request) {
        return registrationService.registerPetProvider(request);
    }
    @GetMapping
    public PageResponse<PetProviderSummaryResponse> getAllPetProviders(Pageable pageable) {
        return registrationService.getAllPetProviders(pageable);
    }

    @GetMapping("/{userId}")
    public PetProviderDetailResponse getPetProviderDetails(Long userId) {
        return registrationService.getPetProviderDetails(userId);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updatePetProvider(@PathVariable(name = "id") Long id,
                                   @RequestBody @Valid PetProviderUpdateRequest request) {
        registrationService.updatePetProvider(id, request);
    }
}
