package com.SegundasHuellas.backend.adopters.internal.application.service;

import com.SegundasHuellas.backend.adopters.internal.application.dto.AdopterRegistrationRequest;
import com.SegundasHuellas.backend.adopters.internal.domain.entity.Adopter;
import com.SegundasHuellas.backend.adopters.internal.domain.enums.AdopterStatus;
import com.SegundasHuellas.backend.adopters.internal.infra.persistence.AdopterRepository;
import com.SegundasHuellas.backend.auth.api.RegistrationService;
import com.SegundasHuellas.backend.auth.api.dto.AuthenticationResponse;
import com.SegundasHuellas.backend.shared.domain.vo.Address;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class AdopterRegistrationService {

    private final RegistrationService registrationService;
    private final AdopterRepository adopterRepository;

    public AuthenticationResponse register(AdopterRegistrationRequest request) {

        Adopter adopter = Adopter.builder()
                                 .firstName(request.firstName())
                                 .lastName(request.lastName())
                                 .address(Address.withDefaults())
                                 .status(AdopterStatus.PENDING_VERIFICATION)
                                 .build();

        adopter = adopterRepository.save(adopter);

        return registrationService.register(request.toAuthRequest(), adopter.getId());

    }

}