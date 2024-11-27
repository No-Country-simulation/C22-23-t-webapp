package com.SegundasHuellas.backend.pets.internal.application.service;

import com.SegundasHuellas.backend.pets.api.dto.PetSearchCriteria;
import com.SegundasHuellas.backend.pets.api.dto.PetSearchResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PetSearchService {

    Page<PetSearchResult> searchPets(PetSearchCriteria criteria, Pageable pageable);
}
