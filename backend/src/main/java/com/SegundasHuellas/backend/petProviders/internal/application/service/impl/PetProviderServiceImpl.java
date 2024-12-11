package com.SegundasHuellas.backend.petProviders.internal.application.service.impl;

import com.SegundasHuellas.backend.auth.api.RegistrationService;
import com.SegundasHuellas.backend.auth.api.dto.AuthenticationResponse;
import com.SegundasHuellas.backend.auth.internal.domain.entity.User;
import com.SegundasHuellas.backend.petProviders.internal.application.dto.PetProviderDetailResponse;
import com.SegundasHuellas.backend.petProviders.internal.application.dto.PetProviderRegistrationRequest;
import com.SegundasHuellas.backend.petProviders.internal.application.dto.PetProviderSummaryResponse;
import com.SegundasHuellas.backend.petProviders.internal.application.dto.PetProviderUpdateRequest;
import com.SegundasHuellas.backend.petProviders.internal.application.service.PetProviderService;
import com.SegundasHuellas.backend.petProviders.internal.domain.entity.PetProvider;
import com.SegundasHuellas.backend.petProviders.internal.domain.enums.PetProviderStatus;
import com.SegundasHuellas.backend.petProviders.internal.domain.enums.PetProviderType;
import com.SegundasHuellas.backend.petProviders.internal.infra.persistence.PetProvidersRepository;
import com.SegundasHuellas.backend.shared.application.dto.PageResponse;
import com.SegundasHuellas.backend.shared.domain.vo.Address;
import com.SegundasHuellas.backend.shared.exception.DomainException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.SegundasHuellas.backend.shared.domain.utils.UpdateUtils.updateIfPresent;
import static com.SegundasHuellas.backend.shared.exception.DomainException.ErrorCode.INVALID_DATA;
import static com.SegundasHuellas.backend.shared.exception.DomainException.ErrorCode.RESOURCE_NOT_FOUND;


@Service
@Transactional
@RequiredArgsConstructor
public class PetProviderServiceImpl implements PetProviderService {

    private static final Logger log = LoggerFactory.getLogger(PetProviderServiceImpl.class);
    private final PetProvidersRepository petProviderRepository;

    private final RegistrationService registrationService;

    /**
     * Registers a new pet provider.
     * <p>
     * This method registers a new pet provider. It delegates the user registration to the
     * {@link RegistrationService} and creates a new pet provider with the given name.
     *
     * @param request the registration request
     * @return the authentication response
     */
    @Override
    public AuthenticationResponse registerPetProvider(PetProviderRegistrationRequest request) {
        try {
            PetProvider petProvider = PetProvider.builder()
                    .name(request.name())
                    .type(request.type())
                    .address(Address.withDefaults())
                    .status(PetProviderStatus.PENDING_VERIFICATION)
                    .build();

            petProvider = petProviderRepository.save(petProvider);

            var registrationResults = registrationService.register(request.toAuthRequest(), petProvider.getId());

            petProvider.setUserId(registrationResults.userId());

            return registrationResults;

        } catch (Exception e) {
            log.error("Failed to register pet provider", e);
            // If the user registration fails, throw a domain exception
            throw new DomainException(INVALID_DATA, e.getMessage());
        }
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

    /**
     * Updates an existing pet provider with the details provided in the update request.
     *
     * @param id      the ID of the pet provider to be updated
     * @param request the update request containing new pet provider details
     * @throws DomainException if the pet provider is not found
     */
    @Override
    public void updatePetProvider(Long id, PetProviderUpdateRequest request) {
        // Retrieve the pet provider by ID, throw an exception if not found
        PetProvider petProvider = petProviderRepository.findById(id)
                .orElseThrow(() -> new DomainException(RESOURCE_NOT_FOUND, id.toString()));

        // Update the pet provider with the new details from the request
        updatePetProviderFromRequest(petProvider, request);

        // Save the updated pet provider back to the repository
        petProviderRepository.save(petProvider);
    }

    /**
     * Updates the fields of a PetProvider entity from a PetProviderUpdateRequest.
     *
     * @param petProvider the pet provider entity to update
     * @param update      the update request containing new values
     */
    private void updatePetProviderFromRequest(PetProvider petProvider, PetProviderUpdateRequest update) {
        // Update the name if present in the request
        updateIfPresent(update.name(), petProvider::setName);

        // Update the phone number if present in the request
        updateIfPresent(update.phoneNumber(), petProvider::setPhoneNumber);

        // Update the pet provider type if present in the request
        updateIfPresent(update.type(), petProvider::setType);

        // Update the description if present in the request
        updateIfPresent(update.description(), petProvider::setDescription);

        // Update the street address if present in the request
        updateIfPresent(update.street(), petProvider::setStreet);

        // Update the city if present in the request
        updateIfPresent(update.city(), petProvider::setCity);

        // Update the state if present in the request
        updateIfPresent(update.state(), petProvider::setState);

        // Update the zip code if present in the request
        updateIfPresent(update.zip(), petProvider::setZip);

        // Update the country if present in the request
        updateIfPresent(update.country(), petProvider::setCountry);

        // Update the status if present in the request
        updateIfPresent(update.status(), petProvider::setStatus);
    }
}

