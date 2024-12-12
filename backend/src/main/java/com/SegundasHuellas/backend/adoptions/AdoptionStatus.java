package com.SegundasHuellas.backend.adoptions;

import lombok.Getter;

@Getter
public enum AdoptionStatus {
    PENDING("Pendiente"),
    ACCEPTED("Aceptada"),
    REJECTED("Rechazada");

    private final String translation;

    AdoptionStatus(String translation) {
        this.translation = translation;
    }
}
