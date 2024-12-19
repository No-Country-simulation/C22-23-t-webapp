package com.SegundasHuellas.backend.adoptions.infra;

import com.SegundasHuellas.backend.adoptions.domain.entity.AdoptionRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdoptionsRequestRepository extends JpaRepository<AdoptionRequest, Long> {

}
