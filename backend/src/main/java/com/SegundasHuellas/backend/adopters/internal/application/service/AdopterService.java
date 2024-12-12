package com.SegundasHuellas.backend.adopters.internal.application.service;

import com.SegundasHuellas.backend.adopters.internal.application.dto.AdopterDetailsResponse;
import com.SegundasHuellas.backend.adopters.internal.application.dto.AdopterRegistrationRequest;
import com.SegundasHuellas.backend.adopters.internal.application.dto.AdopterSummaryResponse;
import com.SegundasHuellas.backend.adopters.internal.application.dto.AdopterUpdateRequest;
import com.SegundasHuellas.backend.adopters.internal.domain.entity.Adopter;
import com.SegundasHuellas.backend.adopters.internal.domain.enums.AdopterStatus;
import com.SegundasHuellas.backend.adopters.internal.infra.persistence.AdopterRepository;
import com.SegundasHuellas.backend.auth.api.RegistrationService;
import com.SegundasHuellas.backend.auth.api.dto.AuthenticationResponse;
import com.SegundasHuellas.backend.shared.application.dto.PageResponse;
import com.SegundasHuellas.backend.shared.domain.vo.Address;
import com.SegundasHuellas.backend.shared.domain.vo.Image;
import com.SegundasHuellas.backend.shared.domain.vo.ImageDefaults;
import com.SegundasHuellas.backend.shared.exception.DomainException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.SegundasHuellas.backend.shared.domain.utils.UpdateUtils.updateIfPresent;
import static com.SegundasHuellas.backend.shared.exception.DomainException.ErrorCode.RESOURCE_NOT_FOUND;

@Service
@Transactional
@RequiredArgsConstructor
public class AdopterService {

    private final RegistrationService registrationService;
    private final AdopterRepository adopterRepository;

    public AuthenticationResponse register(AdopterRegistrationRequest request) {

        Adopter adopter = Adopter.builder()
                                 .firstName(request.firstName())
                                 .lastName(request.lastName())
                                 .address(Address.withDefaults())
                                 .profilePhoto(Image.fromUrl(ImageDefaults.getDefaultUserProfilePhoto()))
                                 .status(AdopterStatus.PENDING_VERIFICATION)
                                 .build();

        adopter = adopterRepository.save(adopter);

        var registrationResults = registrationService.register(request.toAuthRequest(), adopter.getId());
        adopter.setUserId(registrationResults.userId());

        return registrationResults;

    }

    public AdopterDetailsResponse getAdopterDetails(Long userId) {
        var userDetails = registrationService.getUserDetails(userId);
        var adopterDetails = adopterRepository.findByUserId(userId)
                                              .orElseThrow(() -> new DomainException(RESOURCE_NOT_FOUND, userId.toString()));

        return AdopterDetailsResponse
                .from(adopterDetails)
                .withUserDetails(userDetails);
    }

    public PageResponse<AdopterSummaryResponse> getAdopters(Pageable pageable) {
        return PageResponse.from(adopterRepository.findAllSummaries(pageable));
    }

    public void updateAdopter(Long userId, AdopterUpdateRequest request) {
        Adopter adopter = adopterRepository.findByUserId(userId)
                                           .orElseThrow(() -> new DomainException(RESOURCE_NOT_FOUND, userId.toString()));

        updateAdopterFromRequest(adopter, request);
        adopterRepository.save(adopter);
    }

    private void updateAdopterFromRequest(Adopter adopter, AdopterUpdateRequest update) {
        updateIfPresent(update.firstName(), adopter::setFirstName);
        updateIfPresent(update.lastName(), adopter::setLastName);
        updateIfPresent(update.phoneNumber(), adopter::setPhoneNumber);
        updateIfPresent(update.bio(), adopter::setBio);
        updateIfPresent(update.street(), adopter::setStreet);
        updateIfPresent(update.city(), adopter::setCity);
        updateIfPresent(update.state(), adopter::setState);
        updateIfPresent(update.zip(), adopter::setZip);
        updateIfPresent(update.country(), adopter::setCountry);
        updateIfPresent(update.status(), adopter::setStatus);
    }


}