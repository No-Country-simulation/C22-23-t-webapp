package com.SegundasHuellas.backend.petProviders.internal.application.service.impl;

import com.SegundasHuellas.backend.auth.api.RegistrationService;
import com.SegundasHuellas.backend.auth.api.dto.AuthenticationResponse;
import com.SegundasHuellas.backend.petProviders.internal.application.dto.PetProviderRegistrationRequest;
import com.SegundasHuellas.backend.petProviders.internal.application.service.PetProviderRegistrationService;
import com.SegundasHuellas.backend.petProviders.internal.domain.entity.PetProvider;
import com.SegundasHuellas.backend.petProviders.internal.domain.enums.PetProviderStatus;
import com.SegundasHuellas.backend.petProviders.internal.infra.persistence.PetProvidersRepository;
import com.SegundasHuellas.backend.shared.domain.vo.Address;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
@RequiredArgsConstructor
public class PetProviderRegistrationServiceImpl implements PetProviderRegistrationService {

    private final PetProvidersRepository  petProviderRepository;

    private final RegistrationService registrationService;

    @Override
    public AuthenticationResponse registerPetProvider(PetProviderRegistrationRequest request) {

        PetProvider petProvider = PetProvider.builder()
                .name(request.name())
                .address(Address.withDefaults())
                .status(PetProviderStatus.PENDING_VERIFICATION)
                .build();

        petProvider = petProviderRepository.save(petProvider);

        return registrationService.register(request.toAuthRequest(), petProvider.getId());
    }
}
