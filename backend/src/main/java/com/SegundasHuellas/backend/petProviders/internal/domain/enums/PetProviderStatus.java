package com.SegundasHuellas.backend.petProviders.internal.domain.enums;

import lombok.Getter;

@Getter
public enum PetProviderStatus {
    PENDING_VERIFICATION("Verificación pendiente"),
    VERIFIED("Verificado"),
    REJECTED("Rechazado"),
    SUSPENDED("Suspendido");

    private final String translation;

    PetProviderStatus(String translation) {
        this.translation = translation;
    }
}
