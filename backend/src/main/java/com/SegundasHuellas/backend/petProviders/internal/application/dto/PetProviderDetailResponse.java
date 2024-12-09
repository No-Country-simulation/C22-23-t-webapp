package com.SegundasHuellas.backend.petProviders.internal.application.dto;

import com.SegundasHuellas.backend.auth.api.dto.UserDetailsResponse;
import com.SegundasHuellas.backend.petProviders.internal.domain.entity.PetProvider;
import com.SegundasHuellas.backend.petProviders.internal.domain.enums.PetProviderStatus;
import com.SegundasHuellas.backend.shared.domain.vo.Address;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record PetProviderDetailResponse(
    Long id,
    String name,
    String phoneNumber,
    PetProviderStatus status,
    Address address,
    LocalDateTime createdAt,
    LocalDateTime lastModifiedAt,
    int profileCompletionScore,
    UserDetailsResponse userDetails
) {
    public PetProviderDetailResponse withUserDetails(UserDetailsResponse userDetails) {
        return PetProviderDetailResponse.builder()
                                       .id(id)
                                       .name(name)
                                       .phoneNumber(phoneNumber)
                                       .status(status)
                                       .address(address)
                                       .createdAt(createdAt)
                                       .lastModifiedAt(lastModifiedAt)
                                       .profileCompletionScore(profileCompletionScore)
                                       .userDetails(userDetails)
                                       .build();
    }

    public static PetProviderDetailResponse from(PetProvider petProvider) {
        return PetProviderDetailResponse.builder()
                                       .id(petProvider.getId())
                                       .name(petProvider.getName())
                                       .phoneNumber(petProvider.getPhoneNumber())
                                       .status(petProvider.getStatus())
                                       .address(petProvider.getAddress())
                                       .createdAt(petProvider.getCreatedAt())
                                       .lastModifiedAt(petProvider.getLastModifiedAt())
                                       .profileCompletionScore(petProvider.getProfileCompletionScore())
                                       .build();
    }
}
