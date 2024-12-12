package com.SegundasHuellas.backend.pets.internal.application.service;

import com.SegundasHuellas.backend.auth.api.SecurityPort;
import com.SegundasHuellas.backend.pets.internal.infra.persistence.PetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PetSecurityService {

    private final SecurityPort securityPort;
    private final PetRepository petRepository;

    public boolean isResourceOwner(Long petId){
        Long currentUserId = securityPort.getCurrentUserId();

        return petRepository.findById(petId)
                .map(pet -> pet.getProviderId().equals(currentUserId))
                .orElse(false);

    }

}
