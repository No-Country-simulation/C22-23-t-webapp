package com.SegundasHuellas.backend.adoptions.application.dto;

import com.SegundasHuellas.backend.adoptions.domain.entity.AdoptionRequest;

public record AdoptionsRequestDetailsResponse(
    Long id,
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
    public static AdoptionsRequestDetailsResponse from (AdoptionRequest adoptionRequest) {

        return new AdoptionsRequestDetailsResponse(
            adoptionRequest.getId(),
            adoptionRequest.getAdopterFirstName(),
            adoptionRequest.getAdopterLastName(),
            adoptionRequest.getEmail(),
            adoptionRequest.getPhone(),
            adoptionRequest.getAddress().getStreet(),
            adoptionRequest.getAddress().getCity(),
            adoptionRequest.getAddress().getState(),
            adoptionRequest.getAddress().getZip(),
            adoptionRequest.getAddress().getCountry(),
            adoptionRequest.getComments(),
            adoptionRequest.getPetId(),
            adoptionRequest.getPetProviderId()
        );
    }
}
