package com.SegundasHuellas.backend.adopters.internal.application.dto;

import com.SegundasHuellas.backend.adopters.internal.domain.enums.AdopterStatus;
import com.SegundasHuellas.backend.shared.application.validation.NotBlankIfPresent;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Request payload for updating adopter profile information. All fields are optional, but if present, they cannot be blank.")
public record AdopterUpdateRequest(

        @Schema(
                description = "Adopter's first name. Optional, but cannot be blank if provided.",
                example = "John",
                nullable = true
        )
        @NotBlankIfPresent
        String firstName,

        @Schema(
                description = "Adopter's last name. Optional, but cannot be blank if provided.",
                example = "Doe",
                nullable = true
        )
        @NotBlankIfPresent
        String lastName,

        @Schema(
                description = "Contact phone number. Optional, but cannot be blank if provided.",
                example = "+56912345678",
                nullable = true
        )
        @NotBlankIfPresent
        String phoneNumber,

        @Schema(
                description = "Personal biography or description. Optional, but cannot be blank if provided.",
                example = "Animal lover with 5 years of experience in pet care...",
                nullable = true
        )
        @NotBlankIfPresent
        String bio,

        @Schema(
                description = "Street address. Optional, but cannot be blank if provided.",
                example = "123 Main St",
                nullable = true
        )
        @NotBlankIfPresent
        String street,

        @Schema(
                description = "City name. Optional, but cannot be blank if provided.",
                example = "Santiago",
                nullable = true
        )
        @NotBlankIfPresent
        String city,

        @Schema(
                description = "State or region. Optional, but cannot be blank if provided.",
                example = "Región Metropolitana",
                nullable = true
        )
        @NotBlankIfPresent
        String state,

        @Schema(
                description = "Postal code. Optional, but cannot be blank if provided.",
                example = "7500000",
                nullable = true
        )
        @NotBlankIfPresent
        String zip,

        @Schema(
                description = "Country name. Optional, but cannot be blank if provided.",
                example = "Chile",
                nullable = true
        )
        @NotBlankIfPresent
        String country,

        @Schema(
                description = "Current status of the adopter profile",
                example = "VERIFIED",
                allowableValues = {"PENDING_VERIFICATION", "VERIFIED", "REJECTED", "SUSPENDED"}
        )
        AdopterStatus status
) {


}
