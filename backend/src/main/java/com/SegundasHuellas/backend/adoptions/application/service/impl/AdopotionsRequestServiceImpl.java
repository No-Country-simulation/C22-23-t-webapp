package com.SegundasHuellas.backend.adoptions.application.service.impl;

import com.SegundasHuellas.backend.adoptions.application.dto.AdoptionsRequestDetailsResponse;
import com.SegundasHuellas.backend.adoptions.application.dto.CreateAdoptionsRequestDto;
import com.SegundasHuellas.backend.adoptions.application.service.AdoptionsRequestService;
import com.SegundasHuellas.backend.adoptions.domain.entity.AdoptionRequest;
import com.SegundasHuellas.backend.adoptions.domain.enums.AdoptionStatus;
import com.SegundasHuellas.backend.adoptions.infra.AdoptionsRequestRepository;
import com.SegundasHuellas.backend.shared.domain.vo.Address;
import com.SegundasHuellas.backend.shared.exception.DomainException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
    public List<AdoptionsRequestDetailsResponse> findAllByAdopterId(Long adopterId) {return adoptionsRequestRepository.findAllByAdopterId(adopterId).stream().map(AdoptionsRequestDetailsResponse::from).toList();}

    @Override
    public List<AdoptionsRequestDetailsResponse> findAllByPetId(Long petId) {return adoptionsRequestRepository.findAllByPetId(petId).stream().map(AdoptionsRequestDetailsResponse::from).toList();}

    @Override
    public List<AdoptionsRequestDetailsResponse> findAllByPetProviderId(Long petProviderId) {return adoptionsRequestRepository.findAllByPetProviderId(petProviderId).stream().map(AdoptionsRequestDetailsResponse::from).toList();}

    @Override
    public AdoptionsRequestDetailsResponse findById(Long id) {return adoptionsRequestRepository.findById(id).map(AdoptionsRequestDetailsResponse::from).orElseThrow(() -> new DomainException(RESOURCE_NOT_FOUND));}
}
