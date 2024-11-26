package com.SegundasHuellas.backend.pets.internal.domain.dto;

import java.time.LocalDate;

public record UpdatePetRequestDto(
        String name,
        String species,
        String breedName,
        Integer ageInDays,
        Boolean isCastrated,
        String gender,
        String healthStatus,
        String comments,
        String status
) {
}
