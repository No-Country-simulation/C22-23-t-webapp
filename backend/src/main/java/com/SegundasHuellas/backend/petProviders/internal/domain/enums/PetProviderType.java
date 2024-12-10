package com.SegundasHuellas.backend.petProviders.internal.domain.enums;

import lombok.Getter;

@Getter
public enum PetProviderType {

    SHELTER("Refugio"),
    RESCUER("Rescatista");

    private final String translation;

    PetProviderType(String translation) {this.translation = translation;}
}
