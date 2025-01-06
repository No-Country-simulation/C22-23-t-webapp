package com.SegundasHuellas.backend.adoptions.domain.enums;

import lombok.Getter;

@Getter
public enum AdoptionStatus {
    VERIFIED("Verificado"),
    REJECTED("Rechazado"),
    PENDING_VERIFICATION("Verificación pendiente"),
    PENDING_ADOPTION("Adopcion pendiente"),
    ADOPTED("Adoptado"),
    SUSPENDED("Suspendido");

    private final String translation;

    AdoptionStatus(String translation) {
        this.translation = translation;
    }
}
