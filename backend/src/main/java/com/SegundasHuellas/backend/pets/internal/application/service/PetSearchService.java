package com.SegundasHuellas.backend.pets.internal.application.service;

import com.SegundasHuellas.backend.pets.internal.application.dto.PetSearchCriteria;
import com.SegundasHuellas.backend.pets.internal.application.dto.PetSearchResult;
import com.SegundasHuellas.backend.shared.application.dto.PageResponse;
import org.springframework.data.domain.Pageable;

public interface PetSearchService {

    PageResponse<PetSearchResult> searchPets(PetSearchCriteria criteria, Pageable pageable);
}
