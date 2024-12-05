package com.SegundasHuellas.backend.pets.internal.domain.enums;

import lombok.Getter;

/**
 * Represents the gender of a pet.
 * <p>
 * Each gender is associated with a human-readable translation.
 * </p>
 *
 */
@Getter
public enum Gender {
    MALE("Masculino"),
    FEMALE("Femenino"),
    UNDEFINED("No definido");

    private final String translation;

    /**
     * Constructor for `Gender`.
     *
     * @param translation the human-readable translation of the gender
     */
    Gender(String translation) {
        this.translation = translation;
    }

}