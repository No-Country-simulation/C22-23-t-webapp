package com.SegundasHuellas.backend.petProviders.internal.application.dto;

import com.SegundasHuellas.backend.auth.api.dto.UserDetailsResponse;
import com.SegundasHuellas.backend.petProviders.internal.domain.entity.PetProvider;
import com.SegundasHuellas.backend.petProviders.internal.domain.enums.PetProviderStatus;
import com.SegundasHuellas.backend.shared.domain.vo.Address;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record PetProviderDetailsResponse(
        Long id,
        String name,
        String phoneNumber,
        PetProviderStatus status,
        String description,
        Address address,
        LocalDateTime createdAt,
        LocalDateTime lastModifiedAt,
        int profileCompletionScore,
        UserDetailsResponse userDetails
) {
    public PetProviderDetailsResponse withUserDetails(UserDetailsResponse userDetails) {
        return PetProviderDetailsResponse.builder()
                .id(id)
                .name(name)
                .phoneNumber(phoneNumber)
                .status(status)
                .description(description)
                .address(address)
                .createdAt(createdAt)
                .lastModifiedAt(lastModifiedAt)
                .profileCompletionScore(profileCompletionScore)
                .userDetails(userDetails)
                .build();
    }

    public static PetProviderDetailsResponse from(PetProvider petProvider) {
        return PetProviderDetailsResponse.builder()
                .id(petProvider.getId())
                .name(petProvider.getName())
                .phoneNumber(petProvider.getPhoneNumber())
                .status(petProvider.getStatus())
                .description(petProvider.getDescription())
                .address(petProvider.getAddress())
                .createdAt(petProvider.getCreatedAt())
                .lastModifiedAt(petProvider.getLastModifiedAt())
                .profileCompletionScore(petProvider.getProfileCompletionScore())
                .build();
    }
}
