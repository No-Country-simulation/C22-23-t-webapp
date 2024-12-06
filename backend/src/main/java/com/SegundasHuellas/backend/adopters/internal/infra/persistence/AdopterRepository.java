package com.SegundasHuellas.backend.adopters.internal.infra.persistence;

import com.SegundasHuellas.backend.adopters.internal.application.dto.AdopterDetailsResponse;
import com.SegundasHuellas.backend.adopters.internal.domain.entity.Adopter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AdopterRepository extends JpaRepository<Adopter, Long> {


    @Query("""
            select new com.SegundasHuellas.backend.adopters.internal.application.dto.AdopterDetailsResponse(
            a.userId,
            a.firstName,
            a.lastName,
            a.status
            )
            from Adopter a
            where a.userId = :userId
            """)
    Optional<AdopterDetailsResponse> findByUserId(@Param("userId") Long userId);


}
