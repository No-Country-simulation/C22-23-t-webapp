package com.SegundasHuellas.backend.pets.internal.domain.enums;

import lombok.Getter;

@Getter
public enum PetStatus {
    AVAILABLE("Disponible"),
    UNAVAILABLE("No disponible"),
    ADOPTED("Adoptado");

    private final String translation;

    PetStatus(String translation) {
        this.translation = translation;
    }
}