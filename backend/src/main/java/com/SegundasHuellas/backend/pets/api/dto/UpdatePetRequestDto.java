package com.SegundasHuellas.backend.pets.api.dto;

import java.time.LocalDate;

public record UpdatePetRequestDto(
        String name,
        String species,
        String breedName,
        LocalDate birthDate,
        Integer ageInDays,
        Boolean isCastrated,
        String gender,
        String healthStatus,
        String comments,
        String status
) {
}
