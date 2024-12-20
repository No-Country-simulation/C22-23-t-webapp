package com.SegundasHuellas.backend.adoptions.application.service;


import com.SegundasHuellas.backend.adoptions.application.dto.AdoptionsRequestDetailsResponse;
import com.SegundasHuellas.backend.adoptions.application.dto.CreateAdoptionsRequestDto;

import java.util.List;

public interface AdoptionsRequestService {
    AdoptionsRequestDetailsResponse createAdoptionsRequest (CreateAdoptionsRequestDto adoptionsRequestDto);

    List<AdoptionsRequestDetailsResponse> getAllAdoptionsRequests();

    List<AdoptionsRequestDetailsResponse> findAllByAdopterId(Long adopterId);

    List<AdoptionsRequestDetailsResponse> findAllByPetId(Long petId);

    List<AdoptionsRequestDetailsResponse> findAllByPetProviderId(Long petProviderId);

    AdoptionsRequestDetailsResponse findById(Long id);
}
