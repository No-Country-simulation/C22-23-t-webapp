package com.SegundasHuellas.backend.pets.internal.infra.persistence;

import com.SegundasHuellas.backend.pets.internal.application.dto.BreedResponse;
import com.SegundasHuellas.backend.pets.internal.domain.entity.Breed;
import com.SegundasHuellas.backend.pets.internal.domain.enums.Species;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BreedRepository extends JpaRepository<Breed, Long> {


    Optional<Breed> findBySpeciesAndIsSpeciesDefaultTrue(Species species);

    @Query("""
            SELECT new com.SegundasHuellas.backend.pets.internal.application.dto.BreedResponse(
            b.id,
            b.name,
            count(p)
            )
            FROM Breed b
            LEFT JOIN b.pets p
            WHERE b.species = :species
            GROUP BY b.id, b.name
            """)
    List<BreedResponse> findAllBySpeciesAsResponse(@Param("species") Species species);


    List<Breed> findAllBySpecies(Species species);

    Optional<Breed> findByName(String breedName);
}
