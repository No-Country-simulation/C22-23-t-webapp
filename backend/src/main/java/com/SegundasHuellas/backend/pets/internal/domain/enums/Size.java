package com.SegundasHuellas.backend.pets.internal.domain.enums;

import lombok.Getter;

/**
 * Represents the size of a pet.
 * <p>
 * Each size is associated with a human-readable translation.
 * </p>
 *
 */
@Getter
public enum Size {

    SMALL("Pequeño"),
    MEDIUM("Mediano"),
    LARGE("Grande");

    private final String translation;

    /**
     * Constructor for `Size`.
     *
     * @param translation the human-readable translation of the size
     */
    Size(String translation) {this.translation = translation;}
}
