package com.SegundasHuellas.backend.adopters.internal.infra.persistence;

import com.SegundasHuellas.backend.adopters.internal.domain.entity.Adopter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdopterRepository extends JpaRepository<Adopter, Long> {
}
