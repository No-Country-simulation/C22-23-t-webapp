package com.SegundasHuellas.backend.adopters.internal.application.listener;

import com.SegundasHuellas.backend.adopters.internal.domain.entity.Adopter;
import com.SegundasHuellas.backend.adopters.internal.infra.persistence.AdopterRepository;
import com.SegundasHuellas.backend.adoptions.AdoptionRequestedEvent;
import com.SegundasHuellas.backend.shared.exception.DomainException;
import lombok.RequiredArgsConstructor;
import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.stereotype.Component;

import static com.SegundasHuellas.backend.shared.exception.DomainException.ErrorCode.RESOURCE_NOT_FOUND;

@Component
@RequiredArgsConstructor
public class AdopterAdoptionRequestListener {

    private final AdopterRepository adopterRepository;

    @ApplicationModuleListener
    public void onAdoptionRequested(AdoptionRequestedEvent event) {
        Adopter adopter = adopterRepository.findById(event.adopterId())
                                           .orElseThrow(() -> new DomainException(RESOURCE_NOT_FOUND, event.adopterId().toString()));

        adopter.addAdoptionApplication(event.adoptionId());
    }

}
