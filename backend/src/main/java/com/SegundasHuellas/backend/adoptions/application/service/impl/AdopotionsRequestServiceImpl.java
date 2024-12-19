package com.SegundasHuellas.backend.adoptions.application.service.impl;

import com.SegundasHuellas.backend.adoptions.application.dto.CreateAdoptionsRequestDto;
import com.SegundasHuellas.backend.adoptions.application.service.AdoptionsRequestService;
import com.SegundasHuellas.backend.adoptions.domain.entity.AdoptionRequest;
import com.SegundasHuellas.backend.adoptions.infra.AdoptionsRequestRepository;
import com.SegundasHuellas.backend.pets.internal.domain.entity.Pet;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class AdopotionsRequestServiceImpl implements AdoptionsRequestService {

    private final AdoptionsRequestRepository adoptionsRequestRepository;

    public void createAdoptionsRequest (CreateAdoptionsRequestDto adoptionsRequestDto) {



        AdoptionRequest newAdoptionRequest = AdoptionRequest.builder()
                .firstName(adoptionsRequestDto.firstName())
                .lastName(adoptionsRequestDto.lastName())
                .email(adoptionsRequestDto.email())
                .phone(adoptionsRequestDto.phone())
                .address(adoptionsRequestDto.address())
                .city(adoptionsRequestDto.city())
                .state(adoptionsRequestDto.state())
                .zip(adoptionsRequestDto.zip())
                .country(adoptionsRequestDto.country())
                .comments(adoptionsRequestDto.comments())
                .build();
    }

}
