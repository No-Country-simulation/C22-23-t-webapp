package com.SegundasHuellas.backend.adopters.internal.application.dto;

import com.SegundasHuellas.backend.adopters.internal.domain.enums.AdopterStatus;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Simplified adopter information response for list views")
public record AdopterSummaryResponse(
        @Schema(
                description = "Unique identifier of the adopter",
                example = "1234"
        )
        Long adopterId,

        @Schema(
                description = "Adopter's first name",
                example = "John"
        )
        String firstName,

        @Schema(
                description = "Adopter's last name",
                example = "Doe"
        )
        String lastName,

        @Schema(
                description = "Current status of the adopter profile",
                example = "VERIFIED",
                allowableValues = {"PENDING_VERIFICATION", "VERIFIED", "REJECTED", "SUSPENDED"}
        )
        String status
) {

    public AdopterSummaryResponse(Long adopterId, String firstName, String lastName, AdopterStatus status) {
        this(adopterId, firstName, lastName, status.getTranslation());
    }
}
