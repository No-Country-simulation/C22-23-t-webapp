package com.SegundasHuellas.backend.pets.internal.application.dto;

import com.SegundasHuellas.backend.pets.internal.domain.enums.Gender;
import com.SegundasHuellas.backend.pets.internal.domain.enums.PetStatus;
import com.SegundasHuellas.backend.pets.internal.domain.enums.Size;
import com.SegundasHuellas.backend.pets.internal.domain.enums.Species;
import com.SegundasHuellas.backend.shared.application.dto.ImageResponse;
import com.SegundasHuellas.backend.shared.domain.vo.Image;

import java.util.ArrayList;

public record PetSearchResult(
        Long id,
        String name,
        String species,
        Integer ageInDays,
        String gender,
        String status,
        String size,
        ImageResponse photo
) {

    //Constructor for JPQL
    public PetSearchResult(Long id, String name, Species species, Integer ageInDays,
                           Gender gender, PetStatus status, Size size, Image mainPhoto) {
        this(id, name, species.getTranslation(), ageInDays, gender.getTranslation(), status.getTranslation(),
                size.getTranslation(), ImageResponse.from(mainPhoto));
    }

}
