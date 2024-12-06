package com.SegundasHuellas.backend.adopters.internal.application.dto;

import com.SegundasHuellas.backend.adopters.internal.domain.enums.AdopterStatus;
import com.SegundasHuellas.backend.auth.api.dto.UserDetailsResponse;

public record AdopterDetailsResponse(
        Long userId,
        String firstName,
        String lastName,
        String status,
        UserDetailsResponse userDetails
) {

    public AdopterDetailsResponse(Long userId,
                                  String firstName,
                                  String lastName,
                                  AdopterStatus status) {

        this(userId, firstName, lastName, status.getTranslation(), null);
    }

    public AdopterDetailsResponse withUserDetails(UserDetailsResponse userDetails) {
        return new AdopterDetailsResponse(userId, firstName, lastName, status, userDetails);
    }
}
