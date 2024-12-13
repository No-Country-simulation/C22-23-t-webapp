package com.SegundasHuellas.backend.pets.internal.application.dto;

import com.SegundasHuellas.backend.pets.internal.domain.enums.PetStatus;
import com.SegundasHuellas.backend.pets.internal.domain.enums.Size;
import com.SegundasHuellas.backend.pets.internal.domain.enums.Species;
import com.SegundasHuellas.backend.pets.internal.domain.vo.Age;

public record PetSearchCriteria(
        String name,
        Species species,
        String breed,
        PetStatus status,
        Size size,
        Age minAge,
        Age maxAge
) {}
