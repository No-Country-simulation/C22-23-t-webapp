package com.SegundasHuellas.backend.petProviders.internal.infra.persistence;

import com.SegundasHuellas.backend.petProviders.internal.domain.entity.PetProvider;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PetProvidersRepository extends JpaRepository<PetProvider, Long> {
}
