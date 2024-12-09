package com.SegundasHuellas.backend.adopters.internal.application.dto;

import com.SegundasHuellas.backend.adopters.internal.domain.enums.AdopterStatus;

public record AdopterSummaryResponse(
        Long adopterId,
        String firstName,
        String lastName,
        String status
) {

    public AdopterSummaryResponse(Long adopterId, String firstName, String lastName, AdopterStatus status) {
        this(adopterId, firstName, lastName, status.getTranslation());
    }
}
