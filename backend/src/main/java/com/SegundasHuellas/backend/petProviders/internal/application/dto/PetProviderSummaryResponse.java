package com.SegundasHuellas.backend.petProviders.internal.application.dto;

import com.SegundasHuellas.backend.petProviders.internal.domain.enums.PetProviderStatus;

public record PetProviderSummaryResponse(
        Long id,
        String name,
        String status
) {
    public PetProviderSummaryResponse(Long id, String name, PetProviderStatus status) {
        this(id, name, status.getTranslation());
    }
}
