package com.SegundasHuellas.backend.adopters.internal.application.dto;

import com.SegundasHuellas.backend.adopters.internal.domain.enums.AdopterStatus;
import com.SegundasHuellas.backend.shared.application.validation.NotBlankIfPresent;

public record AdopterUpdateRequest(
        @NotBlankIfPresent
        String firstName,

        @NotBlankIfPresent
        String lastName,

        @NotBlankIfPresent
        String phoneNumber,

        @NotBlankIfPresent
        String bio,

        @NotBlankIfPresent
        String street,

        @NotBlankIfPresent
        String city,

        @NotBlankIfPresent
        String state,

        @NotBlankIfPresent
        String zip,

        @NotBlankIfPresent
        String country,

        AdopterStatus status
) {


}
