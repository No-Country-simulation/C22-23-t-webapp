package com.SegundasHuellas.backend.petProviders.internal.infra.web;


import com.SegundasHuellas.backend.auth.api.dto.AuthenticationResponse;
import com.SegundasHuellas.backend.petProviders.internal.application.dto.PetProviderDetailsResponse;
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
public class PetProviderAuthController implements PetProviderAuthApi{

    private final PetProviderService registrationService;


    /**
     * Registers a new pet provider.
     *
     * @param request the registration request
     * @return the authentication response
     */
    @Override
    @PostMapping("/register")
    public AuthenticationResponse registerPetProvider(@RequestBody @Valid PetProviderRegistrationRequest request) {
        return registrationService.registerPetProvider(request);
    }

    /**
     * Retrieves a page of all pet providers.
     *
     * @param pageable the pagination configuration
     * @return a page response containing the pet providers
     */
    @GetMapping
    public PageResponse<PetProviderSummaryResponse> getAllPetProviders(Pageable pageable) {
        return registrationService.getAllPetProviders(pageable);
    }

    /**
     * Retrieves the details of a pet provider with the given user ID.
     *
     * @param userId the ID of the user
     * @return the pet provider details response
     */
    @GetMapping("/{userId}")
    public PetProviderDetailsResponse getPetProviderDetails(@PathVariable(name = "userId") Long userId) {
        return registrationService.getPetProviderDetails(userId);
    }

    /**
     * Updates the details of an existing pet provider.
     *
     * @param id    the ID of the pet provider
     * @param request the update request
     */
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updatePetProvider(@PathVariable(name = "id") Long id,
                                  @RequestBody @Valid PetProviderUpdateRequest request) {
        registrationService.updatePetProvider(id, request);
    }
}
