package com.SegundasHuellas.backend.pets.internal.domain.enums;

import lombok.Getter;

@Getter
public enum Gender {
    MALE("Masculino"),
    FEMALE("Femenino"),
    UNDEFINED("No definido");

    private final String translation;

    Gender(String translation) {
        this.translation = translation;
    }

}