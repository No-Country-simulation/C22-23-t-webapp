package com.SegundasHuellas.backend.pets.internal.application.service.impl;

import com.SegundasHuellas.backend.pets.api.PetValidationPort;
import com.SegundasHuellas.backend.pets.internal.infra.persistence.PetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PetValidation implements PetValidationPort {

    private final PetRepository petRepository;

    @Override
    public boolean existsById(Long petId) {
        return petRepository.existsById(petId);
    }
}
