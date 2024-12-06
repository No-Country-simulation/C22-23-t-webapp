package com.SegundasHuellas.backend.adopters.internal.application.dto;

import com.SegundasHuellas.backend.adopters.internal.domain.enums.AdopterStatus;

public record AdopterDetailsResponse(
        Long userId,
        String firstName,
        String lastName,
        String status) {

    public AdopterDetailsResponse(Long userId,
                                  String firstName,
                                  String lastName,
                                  AdopterStatus status) {

        this(userId, firstName, lastName, status.getTranslation());
    }
}
