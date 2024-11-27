package com.SegundasHuellas.backend.pets.internal.application.service;

import com.SegundasHuellas.backend.pets.internal.application.dto.BreedResponse;
import com.SegundasHuellas.backend.pets.internal.domain.entity.Breed;
import com.SegundasHuellas.backend.pets.internal.domain.enums.Species;
import com.SegundasHuellas.backend.pets.internal.infra.persistence.BreedRepository;
import com.SegundasHuellas.backend.shared.exception.DomainException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.SegundasHuellas.backend.shared.exception.DomainException.ErrorCode.DUPLICATED_DATA;

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

    public List<BreedResponse> getAllBreedsForSpecies(Species species) {
        return breedRepository.findAllBySpeciesAsResponse(species);
    }

    public Breed createBreed(Breed breed) {
        validateDefaultBreed(breed);
        return breedRepository.save(breed);
    }

    public List<Breed> createManyBreeds(List<Breed> breeds) {
        Map<Species, List<Breed>> breedsBySpecies = breeds.stream()
                                                          .collect(Collectors.groupingBy(Breed::getSpecies));

        breedsBySpecies.forEach((species, speciesBreeds) -> {
            long defaultCount = speciesBreeds.stream()
                                             .filter(Breed::isSpeciesDefault)
                                             .count();

            if (defaultCount > 1) {
                throw DomainException.withCustomMessage(DUPLICATED_DATA,
                        String.format("Multiple default breeds found for species %s", species));
            }

            if (defaultCount == 1) {
                checkIfDefaultExists(species);
            }
        });

        return breedRepository.saveAll(breeds);
    }

    private void validateDefaultBreed(Breed breed) {
        if (breed.isSpeciesDefault()) {
            checkIfDefaultExists(breed.getSpecies());
        }
    }

    private Breed createDefaultBreedForSpecies(Species species) {
        Objects.requireNonNull(species, "Species cannot be null");
        checkIfDefaultExists(species);

        Breed defaultBreed = new Breed();
        defaultBreed.setName(species.getDefaultBreedName());
        defaultBreed.setSpecies(species);
        defaultBreed.setSpeciesDefault(true);
        return breedRepository.save(defaultBreed);
    }

    private void checkIfDefaultExists(Species species) {
        breedRepository.findBySpeciesAndIsSpeciesDefaultTrue(species)
                       .ifPresent(existingDefault -> {
                           throw DomainException.withCustomMessage(DUPLICATED_DATA,
                                   String.format("A default breed already exists for species %s: %s",
                                           species,
                                           existingDefault.getName()));
                       });
    }


}
