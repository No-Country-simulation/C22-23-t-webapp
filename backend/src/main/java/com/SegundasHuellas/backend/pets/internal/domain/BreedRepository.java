package com.SegundasHuellas.backend.pets.internal.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BreedRepository extends JpaRepository<Breed, Long> {

    Optional<Breed> findByNameAndSpecies(String name, Pet.Species species);
}
