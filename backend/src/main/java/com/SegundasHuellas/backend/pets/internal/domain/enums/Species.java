package com.SegundasHuellas.backend.pets.internal.domain.enums;

import lombok.Getter;

/**
 * Represents the species of a pet.
 * <p>
 * Each species is associated with a human-readable translation and a default placeholder for breeds.
 * </p>
 *
 * <p><strong>Note:</strong> The default breed placeholder applies when no specific breed is defined for the species.</p>
 *
 */
@Getter
public enum Species {
    DOG("Perro"),
    CAT("Gato"),
    OTHER("Otro");


    public static final String DEFAULT_BREED_PLACEHOLDER = "Sin raza especificada";

    private final String translation;

    /**
     * Constructor for `Species`.
     *
     * @param translation the human-readable translation of the species
     */
    Species(String translation) {
        this.translation = translation;
    }

    /**
     * Returns the default breed placeholder for the species.
     *
     * @return a default placeholder string indicating no specific breed
     */

    public String getDefaultBreedName() {
        // También se podría dar un nombre distinto por especie, como "Raza de Gato Desconocida", pero quiza no es necesario.
        return DEFAULT_BREED_PLACEHOLDER;
    }
}