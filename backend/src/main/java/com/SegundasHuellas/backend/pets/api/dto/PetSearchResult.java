package com.SegundasHuellas.backend.pets.api.dto;

import com.SegundasHuellas.backend.pets.internal.domain.enums.Gender;
import com.SegundasHuellas.backend.pets.internal.domain.enums.PetStatus;
import com.SegundasHuellas.backend.pets.internal.domain.enums.Species;
import com.SegundasHuellas.backend.shared.application.dto.ImageResponse;

public record PetSearchResult(
        Long id,
        String name,
        Species species,
        Integer ageInDays,
        Gender gender,
        PetStatus status,
        ImageResponse photo
) {}
