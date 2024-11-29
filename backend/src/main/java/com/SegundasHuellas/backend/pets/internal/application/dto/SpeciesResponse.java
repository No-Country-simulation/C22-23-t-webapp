package com.SegundasHuellas.backend.pets.internal.application.dto;

import com.SegundasHuellas.backend.pets.internal.domain.enums.Species;

public record SpeciesResponse(
        String name,
        String translation
) {

    public SpeciesResponse(Species species) {
        this(species.name(), species.getTranslation());

    }
}
