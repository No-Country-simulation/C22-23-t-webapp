package com.SegundasHuellas.backend.petProviders.internal.application.service;

import com.SegundasHuellas.backend.auth.api.dto.AuthenticationResponse;
import com.SegundasHuellas.backend.petProviders.internal.application.dto.PetProviderDetailsResponse;
import com.SegundasHuellas.backend.petProviders.internal.application.dto.PetProviderRegistrationRequest;
import com.SegundasHuellas.backend.petProviders.internal.application.dto.PetProviderSummaryResponse;
import com.SegundasHuellas.backend.petProviders.internal.application.dto.PetProviderUpdateRequest;
import com.SegundasHuellas.backend.shared.application.dto.PageResponse;
import org.springframework.data.domain.Pageable;

public interface PetProviderService {
    AuthenticationResponse registerPetProvider(PetProviderRegistrationRequest request);

    PageResponse<PetProviderSummaryResponse> getAllPetProviders(Pageable pageable);

    PetProviderDetailsResponse getPetProviderDetails(Long userId);

    void updatePetProvider(Long id, PetProviderUpdateRequest request);
}
