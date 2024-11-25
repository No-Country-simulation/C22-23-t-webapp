package com.SegundasHuellas.backend.pets.internal.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PetRepository extends JpaRepository<Pet, Long> {

    List<Pet> findBySpecies(Pet.Species species);

    List<Pet> findByStatus(Pet.PetStatus status);

    List<Pet> findByBreedName(String breedName);

    List<Pet> findBySpeciesAndStatus(Pet.Species species, Pet.PetStatus status);
}
