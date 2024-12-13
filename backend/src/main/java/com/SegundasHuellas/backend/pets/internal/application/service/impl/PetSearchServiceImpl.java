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
        /**
         * This method takes a PetSearchCriteria and a Pageable as input and returns a PageResponse of PetSearchResult.
         * The PetSearchCriteria contains the search information and the Pageable contains the pagination information.
         * The PetSearchQueryImpl is responsible for executing the actual search query.
         * @param criteria The search criteria
         * @param pageable The pagination information
         * @return A PageResponse of PetSearchResult
         */
        return petSearchQuery.pageSearch(criteria, pageable);
    }
}
