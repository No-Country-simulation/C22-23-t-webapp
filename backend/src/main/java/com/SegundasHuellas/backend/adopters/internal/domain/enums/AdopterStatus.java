package com.SegundasHuellas.backend.adopters.internal.domain.enums;

import lombok.Getter;

@Getter
public enum AdopterStatus {
    PENDING_VERIFICATION("Verificación pendiente"),
    VERIFIED("Verificado"),
    REJECTED("Rechazado"),
    SUSPENDED("Suspendido");

    private final String translation;

    AdopterStatus(String translation) {
        this.translation = translation;
    }
    
}


