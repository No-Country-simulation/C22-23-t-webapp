package com.SegundasHuellas.backend.adopters.internal.application.dto;

import com.SegundasHuellas.backend.adopters.internal.domain.entity.Adopter;
import com.SegundasHuellas.backend.auth.api.dto.UserDetailsResponse;
import com.SegundasHuellas.backend.shared.domain.vo.Address;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record AdopterDetailsResponse(
        Long userId,
        String firstName,
        String lastName,
        String phoneNumber,
        String bio,
        Address address,
        String status,
        LocalDateTime createdAt,
        LocalDateTime lastModifiedAt,
        int profileCompletionScore,
        UserDetailsResponse userDetails

) {

    public AdopterDetailsResponse withUserDetails(UserDetailsResponse userDetails) {
        return AdopterDetailsResponse.builder()
                                     .userId(userId)
                                     .firstName(firstName)
                                     .lastName(lastName)
                                     .phoneNumber(phoneNumber)
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
                                     .bio(adopter.getBio())
                                     .address(adopter.getAddress())
                                     .status(adopter.getStatus().getTranslation())
                                     .createdAt(adopter.getCreatedAt())
                                     .lastModifiedAt(adopter.getLastModifiedAt())
                                     .profileCompletionScore(adopter.getProfileCompletionScore())
                                     .build();

    }
}
