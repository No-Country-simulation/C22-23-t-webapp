package com.SegundasHuellas.backend.pets.internal.application.service.impl;

import com.SegundasHuellas.backend.petProviders.api.PetProviderValidationPort;
import com.SegundasHuellas.backend.petProviders.internal.infra.persistence.PetProvidersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PetProviderValidation implements PetProviderValidationPort {

    private final PetProvidersRepository petProvidersRepository;


    @Override
    public boolean existsById(Long petProviderId) {
        return petProvidersRepository.existsById(petProviderId);
    }
}
