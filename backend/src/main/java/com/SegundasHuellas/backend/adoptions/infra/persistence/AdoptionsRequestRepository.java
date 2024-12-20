package com.SegundasHuellas.backend.adoptions.infra.persistence;

import com.SegundasHuellas.backend.adoptions.domain.entity.AdoptionRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdoptionsRequestRepository extends JpaRepository<AdoptionRequest, Long> {

    List<AdoptionRequest> findAllByAdopterId(Long adopterId);

    List<AdoptionRequest> findAllByPetId(Long petId);

    List<AdoptionRequest> findAllByPetProviderId(Long petProviderId);
}
