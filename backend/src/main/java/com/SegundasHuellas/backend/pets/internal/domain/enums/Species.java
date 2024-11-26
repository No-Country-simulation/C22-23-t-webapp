package com.SegundasHuellas.backend.pets.internal.domain.enums;

public enum Species {
    DOG,
    CAT,
    OTHER;


    public static final String DEFAULT_BREED_PLACEHOLDER = "Raza sin definir";

    public String getDefaultBreedName() {
        // También se podría dar un nombre distinto por especie, como "Raza de Gato Desconocida", pero quiza no es necesario.
        return DEFAULT_BREED_PLACEHOLDER;
    }
}