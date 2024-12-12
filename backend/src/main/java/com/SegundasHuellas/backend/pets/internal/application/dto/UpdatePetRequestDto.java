package com.SegundasHuellas.backend.pets.internal.application.dto;

import com.SegundasHuellas.backend.pets.internal.domain.enums.Gender;
import com.SegundasHuellas.backend.pets.internal.domain.enums.PetStatus;
import com.SegundasHuellas.backend.pets.internal.domain.enums.Size;
import com.SegundasHuellas.backend.pets.internal.domain.enums.Species;
import com.SegundasHuellas.backend.shared.application.validation.NotBlankIfPresent;

public record UpdatePetRequestDto(
        @NotBlankIfPresent
        String name,


        @NotBlankIfPresent
        String breedName,

        @NotBlankIfPresent
        Integer ageInDays,

        Size size,

        @NotBlankIfPresent
        Boolean isCastrated,

        PetStatus status,

        Gender gender,

        @NotBlankIfPresent
        String healthStatus,

        @NotBlankIfPresent
        String comments
) {
}
