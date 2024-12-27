package com.SegundasHuellas.backend.adoptions.application.service.impl;

import com.SegundasHuellas.backend.adoptions.application.dto.AdoptionRequestsUpdateDto;
import com.SegundasHuellas.backend.adoptions.application.dto.AdoptionsRequestDetailsResponse;
import com.SegundasHuellas.backend.adoptions.application.dto.CreateAdoptionsRequestDto;
import com.SegundasHuellas.backend.adoptions.application.service.AdoptionsRequestService;
import com.SegundasHuellas.backend.adoptions.domain.entity.AdoptionRequest;
import com.SegundasHuellas.backend.adoptions.domain.enums.AdoptionStatus;
import com.SegundasHuellas.backend.adoptions.infra.persistence.AdoptionsRequestRepository;
import com.SegundasHuellas.backend.shared.domain.vo.Address;
import com.SegundasHuellas.backend.shared.exception.DomainException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.SegundasHuellas.backend.shared.exception.DomainException.ErrorCode.RESOURCE_NOT_FOUND;

@Service
@Transactional
@RequiredArgsConstructor
public class AdopotionsRequestServiceImpl implements AdoptionsRequestService {

    private final AdoptionsRequestRepository adoptionsRequestRepository;

    @Override
    public AdoptionsRequestDetailsResponse createAdoptionsRequest(CreateAdoptionsRequestDto adoptionsRequestDto) {

        Address address = Address.builder()
                .street(adoptionsRequestDto.street())
                .city(adoptionsRequestDto.city())
                .state(adoptionsRequestDto.state())
                .zip(adoptionsRequestDto.zip())
                .country(adoptionsRequestDto.country())
                .build();

        AdoptionRequest newAdoptionRequest = AdoptionRequest.builder()
                .AdopterFirstName(adoptionsRequestDto.firstName())
                .AdopterLastName(adoptionsRequestDto.lastName())
                .email(adoptionsRequestDto.email())
                .phone(adoptionsRequestDto.phone())
                .address(address)
                .comments(adoptionsRequestDto.comments())
                .petId(adoptionsRequestDto.petId())
                .adopterId(adoptionsRequestDto.adopterId())
                .petProviderId(adoptionsRequestDto.petProviderId())
                .status(AdoptionStatus.PENDING_VERIFICATION)
                .build();

        adoptionsRequestRepository.save(newAdoptionRequest);

        return AdoptionsRequestDetailsResponse.from(newAdoptionRequest);
    }

    @Override
    public List<AdoptionsRequestDetailsResponse> getAllAdoptionsRequests(){return adoptionsRequestRepository.findAll().stream().map(AdoptionsRequestDetailsResponse::from).toList();}

    @Override
    public AdoptionsRequestDetailsResponse findByAdopterId(Long adopterId) {
        return adoptionsRequestRepository.findByAdopterId(adopterId)
                .map(AdoptionsRequestDetailsResponse::from)
                .orElseThrow(() -> new DomainException(RESOURCE_NOT_FOUND, adopterId.toString()));
    }

    @Override
    public AdoptionsRequestDetailsResponse findByPetId(Long petId) {
        return adoptionsRequestRepository.findByPetId(petId)
                .map(AdoptionsRequestDetailsResponse::from)
                .orElseThrow(() -> new DomainException(RESOURCE_NOT_FOUND, petId.toString()));
    }

    @Override
    public AdoptionsRequestDetailsResponse findByPetProviderId(Long petProviderId) {
        return adoptionsRequestRepository.findByPetProviderId(petProviderId)
                .map(AdoptionsRequestDetailsResponse::from)
                .orElseThrow(() -> new DomainException(RESOURCE_NOT_FOUND, petProviderId.toString()));
    }

    @Override
    public AdoptionsRequestDetailsResponse findById(Long id) {
        return adoptionsRequestRepository.findById(id)
                .map(AdoptionsRequestDetailsResponse::from)
                .orElseThrow(() -> new DomainException(RESOURCE_NOT_FOUND));
    }

    @Override
    public AdoptionsRequestDetailsResponse updateAdoptionsRequests(Long id, AdoptionRequestsUpdateDto updateDto) {
        AdoptionRequest existingRequest = adoptionsRequestRepository.findById(id).orElseThrow(() -> new DomainException(RESOURCE_NOT_FOUND));

        // Actualizar solo los campos que no son nulos en el DTO
        if (updateDto.adopterFirstName() != null) {
            existingRequest.setAdopterFirstName(updateDto.adopterFirstName());
        }
        if (updateDto.adopterLastName() != null) {
            existingRequest.setAdopterLastName(updateDto.adopterLastName());
        }
        if (updateDto.email() != null) {
            existingRequest.setEmail(updateDto.email());
        }
        if (updateDto.phone() != null) {
            existingRequest.setPhone(updateDto.phone());
        }
        if (updateDto.street() != null) {
            existingRequest.setStreet(updateDto.street());
        }
        if (updateDto.city() != null) {
            existingRequest.setCity(updateDto.city());
        }
        if (updateDto.state() != null) {
            existingRequest.setState(updateDto.state());
        }
        if (updateDto.zip() != null) {
            existingRequest.setZip(updateDto.zip());
        }
        if (updateDto.country() != null) {
            existingRequest.setCountry(updateDto.country());
        }
        if (updateDto.comments() != null) {
            existingRequest.setComments(updateDto.comments());
        }
        if (updateDto.petId() != null) {
            existingRequest.setPetId(updateDto.petId());
        }
        if (updateDto.petProviderId() != null) {
            existingRequest.setPetProviderId(updateDto.petProviderId());
        }

        adoptionsRequestRepository.save(existingRequest);

        return AdoptionsRequestDetailsResponse.from(existingRequest);
    }

    @Override
    public void deleteAdoptionsRequest(Long id) {adoptionsRequestRepository.deleteById(id);}
}
