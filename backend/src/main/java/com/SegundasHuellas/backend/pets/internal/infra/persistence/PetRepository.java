package com.SegundasHuellas.backend.pets.internal.infra.persistence;

import com.SegundasHuellas.backend.pets.internal.domain.entity.Pet;
import com.SegundasHuellas.backend.pets.internal.domain.enums.PetStatus;
import com.SegundasHuellas.backend.pets.internal.domain.enums.Species;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PetRepository extends JpaRepository<Pet, Long> {

//    List<Pet> findBySpecies(Species species);

    List<Pet> findByStatus(PetStatus status);

    List<Pet> findByBreedName(String breedName);

//    List<Pet> findBySpeciesAndStatus(Species species, PetStatus status);
}
