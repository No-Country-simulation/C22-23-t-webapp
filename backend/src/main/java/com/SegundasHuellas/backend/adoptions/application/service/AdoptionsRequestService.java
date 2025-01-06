package com.SegundasHuellas.backend.adoptions.application.service;


import com.SegundasHuellas.backend.adoptions.application.dto.AdoptionRequestsUpdateDto;
import com.SegundasHuellas.backend.adoptions.application.dto.AdoptionsRequestDetailsResponse;
import com.SegundasHuellas.backend.adoptions.application.dto.CreateAdoptionsRequestDto;

import java.util.List;

public interface AdoptionsRequestService {
    AdoptionsRequestDetailsResponse createAdoptionsRequest (CreateAdoptionsRequestDto adoptionsRequestDto);

    List<AdoptionsRequestDetailsResponse> getAllAdoptionsRequests();

    AdoptionsRequestDetailsResponse findByAdopterId(Long adopterId);

    AdoptionsRequestDetailsResponse findByPetId(Long petId);

    AdoptionsRequestDetailsResponse findByPetProviderId(Long petProviderId);

    AdoptionsRequestDetailsResponse findById(Long id);

    AdoptionsRequestDetailsResponse updateAdoptionsRequests(Long id, AdoptionRequestsUpdateDto updateDto);

    void deleteAdoptionsRequest(Long id);
}
