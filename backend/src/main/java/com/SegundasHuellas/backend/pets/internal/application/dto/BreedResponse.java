package com.SegundasHuellas.backend.pets.internal.application.dto;

import lombok.Builder;

@Builder
public record BreedResponse(
        Long id,
        String name,
        Long petsCount
) {
}
