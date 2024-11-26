package com.SegundasHuellas.backend.pets.internal.domain.enums;

import lombok.Getter;


@Getter
public enum Species {
    DOG("Perro"),
    CAT("Gato"),
    OTHER("Otro");


    public static final String DEFAULT_BREED_PLACEHOLDER = "Sin raza especificada";

    private final String translation;

    Species(String translation) {
        this.translation = translation;
    }

    public String getDefaultBreedName() {
        // También se podría dar un nombre distinto por especie, como "Raza de Gato Desconocida", pero quiza no es necesario.
        return DEFAULT_BREED_PLACEHOLDER;
    }
}