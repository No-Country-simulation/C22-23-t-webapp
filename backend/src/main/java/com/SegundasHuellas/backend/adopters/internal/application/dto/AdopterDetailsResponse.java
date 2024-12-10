package com.SegundasHuellas.backend.adopters.internal.application.dto;

import com.SegundasHuellas.backend.adopters.internal.domain.entity.Adopter;
import com.SegundasHuellas.backend.auth.api.dto.UserDetailsResponse;
import com.SegundasHuellas.backend.shared.application.dto.ImageResponse;
import com.SegundasHuellas.backend.shared.domain.vo.Address;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.time.LocalDateTime;

@Schema(description = "Detailed response containing adopter profile information")
@Builder
public record AdopterDetailsResponse(
        @Schema(
                description = "Unique identifier of the adopter",
                example = "1234"
        )
        Long userId,

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
                description = "Adopter's profile photo information"
        )
        ImageResponse profilePhoto,

        @Schema(
                description = "Adopter's contact phone number",
                example = "+56912345678"
        )
        String phoneNumber,

        @Schema(
                description = "Adopter's personal biography or description",
                example = "Animal lover with experience in pet care..."
        )
        String bio,

        @Schema(
                description = "Adopter's physical address information"
        )
        Address address,

        @Schema(
                description = "Current status of the adopter profile",
                example = "VERIFIED",
                allowableValues = {"PENDING_VERIFICATION", "VERIFIED", "REJECTED", "SUSPENDED"}
        )
        String status,

        @Schema(
                description = "Timestamp when the adopter profile was created",
                example = "2024-03-15T10:30:00",
                type = "string",
                format = "date-time"
        )
        LocalDateTime createdAt,

        @Schema(
                description = "Timestamp of last profile modification",
                example = "2024-03-15T10:30:00",
                type = "string",
                format = "date-time"
        )
        LocalDateTime lastModifiedAt,

        @Schema(
                description = "Profile completion percentage (increases when the user completes profile information) (0-10)",
                example = "4",
                minimum = "0",
                maximum = "10"
        )
        int profileCompletionScore,

        @Schema(
                description = "Associated user account details"
        )
        UserDetailsResponse userDetails

) {

    public AdopterDetailsResponse withUserDetails(UserDetailsResponse userDetails) {
        return AdopterDetailsResponse.builder()
                                     .userId(userId)
                                     .firstName(firstName)
                                     .lastName(lastName)
                                     .phoneNumber(phoneNumber)
                                     .profilePhoto(profilePhoto)
                                     .bio(bio)
                                     .address(address)
                                     .status(status)
                                     .createdAt(createdAt)
                                     .lastModifiedAt(lastModifiedAt)
                                     .profileCompletionScore(profileCompletionScore)
                                     .userDetails(userDetails)
                                     .build();

    }

    public static AdopterDetailsResponse from(Adopter adopter) {
        return AdopterDetailsResponse.builder()
                                     .userId(adopter.getUserId())
                                     .firstName(adopter.getFirstName())
                                     .lastName(adopter.getLastName())
                                     .phoneNumber(adopter.getPhoneNumber())
                                     .profilePhoto(ImageResponse.from(adopter.getProfilePhoto()))
                                     .bio(adopter.getBio())
                                     .address(adopter.getAddress())
                                     .status(adopter.getStatus().getTranslation())
                                     .createdAt(adopter.getCreatedAt())
                                     .lastModifiedAt(adopter.getLastModifiedAt())
                                     .profileCompletionScore(adopter.getProfileCompletionScore())
                                     .build();

    }
}
