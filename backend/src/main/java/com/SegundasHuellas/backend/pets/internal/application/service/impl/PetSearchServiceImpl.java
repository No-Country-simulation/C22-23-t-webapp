package com.SegundasHuellas.backend.pets.internal.application.service.impl;

import com.SegundasHuellas.backend.pets.internal.application.dto.PetSearchCriteria;
import com.SegundasHuellas.backend.pets.internal.application.dto.PetSearchResult;
import com.SegundasHuellas.backend.pets.internal.application.service.PetSearchService;
import com.SegundasHuellas.backend.pets.internal.infra.persistence.PetSearchQuery;
import com.SegundasHuellas.backend.shared.application.dto.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PetSearchServiceImpl implements PetSearchService {

    private final PetSearchQuery petSearchQuery;

    @Override
    public PageResponse<PetSearchResult> searchPets(PetSearchCriteria criteria, Pageable pageable) {
        return petSearchQuery.pageSearch(criteria, pageable);
    }
}
