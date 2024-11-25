package com.SegundasHuellas.backend.pets.internal.infra.persistence;

import com.SegundasHuellas.backend.pets.internal.domain.entity.Breed;
import com.SegundasHuellas.backend.pets.internal.domain.entity.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BreedRepository extends JpaRepository<Breed, Long> {

    Optional<Breed> findByNameAndSpecies(String name, Pet.Species species);
}
