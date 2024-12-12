package com.SegundasHuellas.backend.petProviders.internal.application.listener;

import com.SegundasHuellas.backend.adoptions.AdoptionRequestedEvent;
import com.SegundasHuellas.backend.petProviders.internal.domain.entity.PetProvider;
import com.SegundasHuellas.backend.petProviders.internal.infra.persistence.PetProvidersRepository;
import com.SegundasHuellas.backend.shared.exception.DomainException;
import lombok.RequiredArgsConstructor;
import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.stereotype.Component;

import static com.SegundasHuellas.backend.shared.exception.DomainException.ErrorCode.RESOURCE_NOT_FOUND;

@Component
@RequiredArgsConstructor
public class ProviderAdopterApplicationListener {

    private final PetProvidersRepository providersRepository;

    @ApplicationModuleListener
    public void onAdoptionRequested(AdoptionRequestedEvent event) {
        PetProvider provider = providersRepository.findById(event.providerId())
                                                  .orElseThrow(() -> new DomainException(RESOURCE_NOT_FOUND, event.providerId().toString()));

        provider.registerAdoptionApplication(event.adoptionId());
    }

}
