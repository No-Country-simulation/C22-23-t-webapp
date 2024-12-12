package com.SegundasHuellas.backend.adopters.internal.application.service;

import com.SegundasHuellas.backend.adopters.api.AdopterValidationPort;
import com.SegundasHuellas.backend.adopters.internal.infra.persistence.AdopterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdopterValidation implements AdopterValidationPort {

    private final AdopterRepository adopterRepository;


    @Override
    public boolean existsById(Long adopterId) {
        return adopterRepository.existsById(adopterId);
    }
}
