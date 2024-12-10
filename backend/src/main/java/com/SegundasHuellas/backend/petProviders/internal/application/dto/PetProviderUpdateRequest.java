package com.SegundasHuellas.backend.petProviders.internal.application.dto;

import com.SegundasHuellas.backend.petProviders.internal.domain.enums.PetProviderStatus;
import com.SegundasHuellas.backend.petProviders.internal.domain.enums.PetProviderType;
import com.SegundasHuellas.backend.shared.application.validation.NotBlankIfPresent;

public record PetProviderUpdateRequest(
        @NotBlankIfPresent
        String name,
        @NotBlankIfPresent
        String phoneNumber,
        @NotBlankIfPresent
        PetProviderType type,
        @NotBlankIfPresent
        String description,
        @NotBlankIfPresent
        String street,
        @NotBlankIfPresent
        String city,
        @NotBlankIfPresent
        String state,
        @NotBlankIfPresent
        String zip,
        @NotBlankIfPresent
        String country,
        PetProviderStatus status
) {}
