package com.SegundasHuellas.backend.petProviders.internal.application.service;

import com.SegundasHuellas.backend.auth.api.dto.AuthenticationResponse;
import com.SegundasHuellas.backend.petProviders.internal.application.dto.PetProviderRegistrationRequest;

public interface PetProviderRegistrationService {
    AuthenticationResponse registerPetProvider(PetProviderRegistrationRequest request);
}
