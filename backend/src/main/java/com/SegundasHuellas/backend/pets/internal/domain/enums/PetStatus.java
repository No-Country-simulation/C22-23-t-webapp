package com.SegundasHuellas.backend.pets.internal.domain.enums;

import lombok.Getter;

/**
 * Represents the adoption status of a pet.
 * <p>
 * Each status is associated with a human-readable translation.
 * </p>
 *
 */
@Getter
public enum PetStatus {
    AVAILABLE("Disponible"),
    UNAVAILABLE("No disponible"),
    ADOPTED("Adoptado");

    private final String translation;

    /**
     * Constructor for `PetStatus`.
     *
     * @param translation the human-readable translation of the status
     */
    PetStatus(String translation) {
        this.translation = translation;
    }
}