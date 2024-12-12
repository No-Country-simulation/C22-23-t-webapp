package com.SegundasHuellas.backend.adoptions;

import lombok.Builder;

@Builder
public record AdoptionRequestedEvent(Long adoptionId, Long petId, Long providerId, Long adopterId) {
}
