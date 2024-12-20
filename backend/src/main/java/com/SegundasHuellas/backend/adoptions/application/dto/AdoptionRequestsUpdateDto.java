package com.SegundasHuellas.backend.adoptions.application.dto;

public record AdoptionRequestsUpdateDto(
        String adopterFirstName,
        String adopterLastName,
        String email,
        String phone,
        String street,
        String city,
        String state,
        String zip,
        String country,
        String comments,
        Long petId,
        Long petProviderId
) {
}
