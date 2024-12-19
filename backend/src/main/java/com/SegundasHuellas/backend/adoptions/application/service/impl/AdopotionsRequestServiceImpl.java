package com.SegundasHuellas.backend.adoptions.application.service.impl;

import com.SegundasHuellas.backend.adoptions.application.service.AdoptionsRequestService;
import com.SegundasHuellas.backend.adoptions.infra.AdoptionsRequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class AdopotionsRequestServiceImpl implements AdoptionsRequestService {

    private final AdoptionsRequestRepository adoptionsRequestRepository;

    public void createAdoptionsRequest() {

    }

}
