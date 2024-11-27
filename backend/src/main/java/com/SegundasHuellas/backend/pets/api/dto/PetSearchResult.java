package com.SegundasHuellas.backend.pets.api.dto;

import com.SegundasHuellas.backend.pets.internal.domain.enums.Gender;
import com.SegundasHuellas.backend.pets.internal.domain.enums.PetStatus;
import com.SegundasHuellas.backend.pets.internal.domain.enums.Species;

public record PetSearchResult(
        Long id,
        String name,
        Species species,
        String breedName,
        Integer ageInDays,
        Boolean isCastrated,
        Gender gender,
        PetStatus status,
        String comments,
        String healthStatus
) {}
