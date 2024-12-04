package com.SegundasHuellas.backend.adopters.internal.domain.vo;

import com.SegundasHuellas.backend.pets.internal.domain.entity.Breed;
import com.SegundasHuellas.backend.pets.internal.domain.enums.Species;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.util.Set;

@Setter
@Getter
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PetPreference {

    //TODO: WE NEED TO ADD SIZE AND CHARACTERISTICS TO PET MODULE


    private Long speciesId;

    private Long breedId;

    private String size;

    private Set<String> characteristicIds;



}
