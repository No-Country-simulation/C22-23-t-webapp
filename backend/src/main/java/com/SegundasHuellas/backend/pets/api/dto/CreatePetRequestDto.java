package com.SegundasHuellas.backend.pets.api.dto;

import com.SegundasHuellas.backend.pets.internal.domain.enums.Species;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreatePetRequestDto(
        @NotBlank
        String name,

        @NotNull
        Species species) {

}
