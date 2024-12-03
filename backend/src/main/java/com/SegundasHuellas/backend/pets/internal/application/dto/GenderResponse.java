package com.SegundasHuellas.backend.pets.internal.application.dto;

import com.SegundasHuellas.backend.pets.internal.domain.enums.Gender;

public record GenderResponse(
        String name,
        String translation
) {
    public GenderResponse(Gender gender) {
        this(gender.name(), gender.getTranslation());
    }
}
