package com.SegundasHuellas.backend.pets.api.dto;

import com.SegundasHuellas.backend.shared.application.dto.ImageResponse;

import java.time.LocalDate;

public record PetResponseDto(
        Long id,
        String name,
        String species,
        String breedName,
        ImageResponse photo,
        Boolean isCastrated,
        String gender,
        Integer ageInDays,
        String healthStatus,
        String comments,
        String status
) {//❓Devolver birthDate? falta definir su implementación, ya que muchas veces no se sabe cuando es.
}
