package com.SegundasHuellas.backend.pets.internal.application.service;

import com.SegundasHuellas.backend.pets.internal.domain.entity.Breed;
import com.SegundasHuellas.backend.pets.internal.domain.enums.Species;
import com.SegundasHuellas.backend.pets.internal.infra.persistence.BreedRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@Transactional
@RequiredArgsConstructor
public class BreedService {

    private final BreedRepository breedRepository;

    // Entrega la raza por defecto de una especie, si no existe, la crea.
    public Breed getDefaultBreedForSpecies(Species species) {

        return breedRepository.findBySpeciesAndIsSpeciesDefaultTrue(species)
                .orElseGet(() -> createDefaultBreedForSpecies(species));


    }



    private Breed createDefaultBreedForSpecies(Species species) {
        Objects.requireNonNull(species, "Species cannot be null");

        Breed defaultBreed = new Breed();
        defaultBreed.setName(species.getDefaultBreedName());
        defaultBreed.setSpecies(species);
        defaultBreed.setSpeciesDefault(true);
        return breedRepository.save(defaultBreed);
    }




}
