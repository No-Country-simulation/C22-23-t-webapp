package com.SegundasHuellas.backend.petProviders.internal.infra.persistence;

import com.SegundasHuellas.backend.petProviders.internal.application.dto.PetProviderSummaryResponse;
import com.SegundasHuellas.backend.petProviders.internal.domain.entity.PetProvider;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PetProvidersRepository extends JpaRepository<PetProvider, Long> {


    @Query("select p from PetProvider p where p.user.id = :userId")
    Optional<PetProvider> findByUserId(@Param("userId") Long userId);

    @Query("""
            select new com.SegundasHuellas.backend.petProviders.internal.application.dto.PetProviderSummaryResponse
            (p.id,
            p.name,
            p.status
            ) from PetProvider p
            """)
    Page<PetProviderSummaryResponse> findAllSummaries(Pageable pageable);
}
