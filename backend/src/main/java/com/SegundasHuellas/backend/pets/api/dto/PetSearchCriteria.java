package com.SegundasHuellas.backend.pets.api.dto;

import com.SegundasHuellas.backend.pets.internal.domain.enums.PetStatus;
import com.SegundasHuellas.backend.pets.internal.domain.enums.Species;

public record PetSearchCriteria(
        String name,
        Species species,
        String breed,
        PetStatus status
) {}
