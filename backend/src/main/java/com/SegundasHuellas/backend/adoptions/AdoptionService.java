package com.SegundasHuellas.backend.adoptions;

import com.SegundasHuellas.backend.adopters.api.AdopterValidationPort;
import com.SegundasHuellas.backend.petProviders.api.PetProviderValidationPort;
import com.SegundasHuellas.backend.pets.api.PetValidationPort;
import com.SegundasHuellas.backend.shared.exception.DomainException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import static com.SegundasHuellas.backend.shared.exception.DomainException.ErrorCode.INVALID_DATA;

@Service
@RequiredArgsConstructor
public class AdoptionService {

    private final AdoptionRepository adoptionRepository;
    private final ApplicationEventPublisher eventPublisher;

    private final PetValidationPort petValidation;
    private final AdopterValidationPort adopterValidation;
    private final PetProviderValidationPort providerValidation;

    public void processAdoptionRequest(@Valid AdoptionRequest adoptionRequest) {

        validateEntitiesExists(adoptionRequest.adopterId(), adoptionRequest.providerId(), adoptionRequest.petId());

        //We should check if an adoption request from this adopter already exists towards the pet.

        Adoption adoption = adoptionRepository.save(Adoption.builder()
                                                            .petId(adoptionRequest.petId())
                                                            .adopterId(adoptionRequest.adopterId())
                                                            .providerId(adoptionRequest.providerId())
                                                            .status(AdoptionStatus.PENDING)
                                                            .build());

        eventPublisher.publishEvent(AdoptionRequestedEvent.builder()
                                                          .adoptionId(adoption.getId())
                                                          .petId(adoption.getPetId())
                                                          .providerId(adoption.getProviderId())
                                                          .adopterId(adoption.getAdopterId())
                                                          .build());


    }

    private void validateEntitiesExists(Long adopterId, Long providerId, Long petId) {
        boolean petExists = petValidation.existsById(petId);
        boolean providerExists = providerValidation.existsById(providerId);
        boolean adopterExists = adopterValidation.existsById(adopterId);

        if (!petExists || !providerExists || !adopterExists) {
            throw new DomainException(INVALID_DATA,
                    "One or more entities not found. Pet exists: %b, Provider exists: %b, Adopter exists: %b"
                            .formatted(petExists, providerExists, adopterExists));
        }
    }
}
