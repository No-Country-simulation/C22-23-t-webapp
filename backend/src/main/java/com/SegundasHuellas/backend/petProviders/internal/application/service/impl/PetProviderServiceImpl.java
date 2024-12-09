package com.SegundasHuellas.backend.petProviders.internal.application.service.impl;

import com.SegundasHuellas.backend.auth.api.RegistrationService;
import com.SegundasHuellas.backend.auth.api.dto.AuthenticationResponse;
import com.SegundasHuellas.backend.petProviders.internal.application.dto.PetProviderDetailResponse;
import com.SegundasHuellas.backend.petProviders.internal.application.dto.PetProviderRegistrationRequest;
import com.SegundasHuellas.backend.petProviders.internal.application.dto.PetProviderSummaryResponse;
import com.SegundasHuellas.backend.petProviders.internal.application.dto.PetProviderUpdateRequest;
import com.SegundasHuellas.backend.petProviders.internal.application.service.PetProviderService;
import com.SegundasHuellas.backend.petProviders.internal.domain.entity.PetProvider;
import com.SegundasHuellas.backend.petProviders.internal.domain.enums.PetProviderStatus;
import com.SegundasHuellas.backend.petProviders.internal.infra.persistence.PetProvidersRepository;
import com.SegundasHuellas.backend.shared.application.dto.PageResponse;
import com.SegundasHuellas.backend.shared.domain.vo.Address;
import com.SegundasHuellas.backend.shared.exception.DomainException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.SegundasHuellas.backend.shared.domain.utils.UpdateUtils.updateIfPresent;
import static com.SegundasHuellas.backend.shared.exception.DomainException.ErrorCode.RESOURCE_NOT_FOUND;


@Service
@Transactional
@RequiredArgsConstructor
public class PetProviderServiceImpl implements PetProviderService {

    private final PetProvidersRepository  petProviderRepository;

    private final RegistrationService registrationService;

    /**
     * Registers a new pet provider.
     *
     * @param request the registration request
     * @return the authentication response
     */
    @Override
    public AuthenticationResponse registerPetProvider(PetProviderRegistrationRequest request) {
        // Create a new pet provider with the given name, empty address and pending verification status
        PetProvider petProvider = PetProvider.builder()
                .name(request.name())
                .address(Address.withDefaults())
                .status(PetProviderStatus.PENDING_VERIFICATION)
                .build();

        // Save the pet provider to the database
        petProvider = petProviderRepository.save(petProvider);

        // Register the user for the pet provider and return the authentication response
        return registrationService.register(request.toAuthRequest(), petProvider.getId());
    }

    /**
     * Retrieves a page of all pet providers.
     *
     * @param pageable the pagination configuration
     * @return a page response containing the pet providers
     */
    @Override
    public PageResponse<PetProviderSummaryResponse> getAllPetProviders(Pageable pageable) {
        // Retrieve a page of all pet providers
        Page<PetProviderSummaryResponse> summaries = petProviderRepository.findAllSummaries(pageable);

        // Return the page response
        return PageResponse.from(summaries);
    }

    /**
     * Retrieves the details of a pet provider with the given user ID.
     *
     * @param userId the ID of the user
     * @return the pet provider details response
     * @throws DomainException if the pet provider is not found
     */
    @Override
    public PetProviderDetailResponse getPetProviderDetails(Long userId) {
        // Retrieve the user details
        var userDetails = registrationService.getUserDetails(userId);

        // Retrieve the pet provider details
        var petProviderDetails = petProviderRepository.findByUserId(userId)
                .orElseThrow(() -> new DomainException(RESOURCE_NOT_FOUND, userId.toString()));

        // Return the pet provider details response
        return PetProviderDetailResponse
                .from(petProviderDetails)
                .withUserDetails(userDetails);
    }

    @Override
    public void updatePetProvider(Long id, PetProviderUpdateRequest request) {
        PetProvider petProvider = petProviderRepository.findById(id)
                .orElseThrow(() -> new DomainException(RESOURCE_NOT_FOUND, id.toString()));

        updatePetProviderFromRequest(petProvider, request);
        petProviderRepository.save(petProvider);
    }

    private void updatePetProviderFromRequest(PetProvider petProvider, PetProviderUpdateRequest update) {
        updateIfPresent(update.name(), petProvider::setName);
        updateIfPresent(update.phoneNumber(), petProvider::setPhoneNumber);
        updateIfPresent(update.type(), petProvider::setType);
        updateIfPresent(update.description(), petProvider::setDescription);
        updateIfPresent(update.street(), petProvider::setStreet);
        updateIfPresent(update.city(), petProvider::setCity);
        updateIfPresent(update.state(), petProvider::setState);
        updateIfPresent(update.zip(), petProvider::setZip);
        updateIfPresent(update.country(), petProvider::setCountry);
        updateIfPresent(update.status(), petProvider::setStatus);
    }
}

