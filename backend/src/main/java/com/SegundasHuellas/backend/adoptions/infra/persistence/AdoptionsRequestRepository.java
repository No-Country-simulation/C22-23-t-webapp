package com.SegundasHuellas.backend.adoptions.infra.persistence;

import com.SegundasHuellas.backend.adoptions.domain.entity.AdoptionRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AdoptionsRequestRepository extends JpaRepository<AdoptionRequest, Long> {

    Optional<AdoptionRequest> findByAdopterId(Long adopterId);

    Optional<AdoptionRequest> findByPetId(Long petId);

    Optional<AdoptionRequest> findByPetProviderId(Long petProviderId);
}
