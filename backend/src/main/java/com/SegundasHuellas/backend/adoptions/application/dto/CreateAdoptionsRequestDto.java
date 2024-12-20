package com.SegundasHuellas.backend.adoptions.application.dto;

public record CreateAdoptionsRequestDto(
        String firstName,
        String lastName,
        String email,
        String phone,
        String street,
        String city,
        String state,
        String zip,
        String country,
        String comments,
        Long petId,
        Long adopterId,
        Long petProviderId
) {
}
