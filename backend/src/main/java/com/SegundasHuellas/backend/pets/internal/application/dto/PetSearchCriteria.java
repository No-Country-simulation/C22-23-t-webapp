package com.SegundasHuellas.backend.pets.internal.application.dto;

import com.SegundasHuellas.backend.pets.internal.domain.enums.PetStatus;
import com.SegundasHuellas.backend.pets.internal.domain.enums.Species;
import com.SegundasHuellas.backend.pets.internal.domain.vo.Age;
import com.SegundasHuellas.backend.pets.internal.domain.vo.Weight;

public record PetSearchCriteria(
        String name,
        Species species,
        String breed,
        PetStatus status,
        Age age,
        String size
) {}
