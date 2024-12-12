package com.SegundasHuellas.backend.adoptions;

public record AdoptionRequest(
        Long adopterId,
        Long providerId,
        Long petId
) {

}
