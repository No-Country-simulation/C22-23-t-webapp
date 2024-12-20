package com.SegundasHuellas.backend.adoptions.application.service.impl;

import com.SegundasHuellas.backend.adoptions.application.dto.AdoptionsRequestDetailsResponse;
import com.SegundasHuellas.backend.adoptions.application.dto.CreateAdoptionsRequestDto;
import com.SegundasHuellas.backend.adoptions.application.service.AdoptionsRequestService;
import com.SegundasHuellas.backend.adoptions.domain.entity.AdoptionRequest;
import com.SegundasHuellas.backend.adoptions.domain.enums.AdoptionStatus;
import com.SegundasHuellas.backend.adoptions.infra.AdoptionsRequestRepository;
import com.SegundasHuellas.backend.shared.domain.vo.Address;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class AdopotionsRequestServiceImpl implements AdoptionsRequestService {

    private final AdoptionsRequestRepository adoptionsRequestRepository;

    public AdoptionsRequestDetailsResponse createAdoptionsRequest (CreateAdoptionsRequestDto adoptionsRequestDto) {

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

}
