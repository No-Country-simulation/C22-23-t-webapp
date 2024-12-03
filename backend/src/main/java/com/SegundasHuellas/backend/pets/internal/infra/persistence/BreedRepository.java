package com.SegundasHuellas.backend.pets.internal.infra.persistence;

import com.SegundasHuellas.backend.pets.internal.application.dto.BreedResponse;
import com.SegundasHuellas.backend.pets.internal.domain.entity.Breed;
import com.SegundasHuellas.backend.pets.internal.domain.enums.Species;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for managing {@link Breed} entities.
 * <p>
 * This repository provides methods for performing CRUD operations and custom queries
 * related to breeds, such as retrieving default breeds, finding breeds by species,
 * and fetching enriched data for reporting or display purposes.
 * </p>
 *
 * <b>Features:</b>
 * - Find the default breed for a specific species.
 * - Retrieve all breeds filtered by species.
 * - Fetch detailed breed data including pet counts.
 *
 */
public interface BreedRepository extends JpaRepository<Breed, Long> {


    /**
     * Finds a breed that is the default breed for the given species.
     *
     * @param species the species to search for
     * @return an optional containing the default breed, or empty if none exists
     */
    Optional<Breed> findBySpeciesAndIsSpeciesDefaultTrue(Species species);

    /**
     * Finds all breeds associated with the given species and returns a list of {@link BreedResponse} objects
     * with the breed's ID, name, and the number of pets associated with the breed.
     *
     * @param species the species to search for
     * @return a list of breed responses
     */
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
    List<BreedResponse> findAllBySpeciesAsResponse(Species species);


    /**
     * Finds all breeds associated with the given species.
     *
     * @param species the species to search for
     * @return a list of breeds
     */
    List<Breed> findAllBySpecies(Species species);
}
