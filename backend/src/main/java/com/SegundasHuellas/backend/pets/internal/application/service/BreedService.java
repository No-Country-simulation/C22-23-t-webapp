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

/**
 * The {@link BreedService} class provides methods for managing breeds associated with specific species.
 * It supports operations such as retrieving the default breed for a species, creating new breeds,
 * and ensuring no duplicate default breeds exist for a species.
 * <p>
 * This service includes methods for:
 * <ul>
 *     <li>Retrieving the default breed for a species.</li>
 *     <li>Retrieving all breeds for a species.</li>
 *     <li>Creating new individual or multiple breeds.</li>
 *     <li>Validating and ensuring no duplicate default breeds exist for a species.</li>
 * </ul>
 * </p>
 */
@Service
@Transactional
@RequiredArgsConstructor
public class BreedService {

    private final BreedRepository breedRepository;

    /**
     * Retrieves the default breed for a species. If it doesn't exist, creates it.
     *
     * @param species the species for which to retrieve the default breed
     * @return the default breed for the given species
     */
    public Breed getDefaultBreedForSpecies(Species species) {
        // Attempt to find the default breed for the species in the repository
        // If not found, create and return a new default breed
        return breedRepository.findBySpeciesAndIsSpeciesDefaultTrue(species)
                              .orElseGet(() -> createDefaultBreedForSpecies(species));
    }

    /**
     * Retrieves a list of all breeds for a given species.
     * <p>
     * This method queries the repository to find all breeds associated
     * with the specified species and maps them to a list of {@link BreedResponse} objects.
     * </p>
     *
     * @param species the species for which to retrieve breeds
     * @return a list of {@link BreedResponse} objects representing the breeds of the species
     */
    public List<BreedResponse> getAllBreedsForSpecies(Species species) {
        // Retrieve all breeds for the specified species from the repository
        return breedRepository.findAllBySpeciesAsResponse(species);
    }

    /**
     * Creates a new breed with the given characteristics.
     * <p>
     * This method first validates the breed, ensuring that there is no
     * duplicate default breed for the same species. If there is, it throws
     * an exception. If not, it saves the breed to the repository and returns
     * the newly created breed.
     * </p>
     *
     * @param breed the breed to create
     * @return the newly created breed
     */
    public Breed createBreed(Breed breed) {
        // Validate the breed, ensuring there's no duplicate default breed
        validateDefaultBreed(breed);
        // Save the breed to the repository
        return breedRepository.save(breed);
    }

    /**
     * Creates multiple breeds from the given list.
     * <p>
     * This method first groups the breeds by their species, then
     * checks for duplicate default breeds within each group.
     * If a duplicate default breed is found, it throws an exception.
     * If not, it saves the breeds to the repository and returns
     * the list of newly created breeds.
     * </p>
     *
     * @param breeds the list of breeds to create
     * @return the list of newly created breeds
     */
    public List<Breed> createManyBreeds(List<Breed> breeds) {
        // Group the breeds by their species
        Map<Species, List<Breed>> breedsBySpecies = breeds.stream()
                                                          .collect(Collectors.groupingBy(Breed::getSpecies));

        // Iterate over the grouped breeds and check for duplicate default breeds
        breedsBySpecies.forEach((species, speciesBreeds) -> {
            // Count the number of default breeds in the group
            long defaultCount = speciesBreeds.stream()
                                             .filter(Breed::isSpeciesDefault)
                                             .count();

            // If there are more than one default breeds, throw an exception
            if (defaultCount > 1) {
                throw DomainException.withCustomMessage(DUPLICATED_DATA,
                        String.format("Multiple default breeds found for species %s", species));
            }

            // If there is one default breed, check if it already exists in the repository
            if (defaultCount == 1) {
                checkIfDefaultExists(species);
            }
        });

        // Save the breeds to the repository
        return breedRepository.saveAll(breeds);
    }

    /**
     * Validates a breed, ensuring there's no duplicate default breed for the same species.
     * <p>
     * This method checks if the given breed is a default breed (i.e., its {@link Breed#isSpeciesDefault()} is true).
     * If it is, it calls {@link #checkIfDefaultExists(Species)} to check if there's already a default breed for the same species.
     * If there is, it throws an exception. If not, it returns without doing anything.
     * </p>
     *
     * @param breed the breed to validate
     */
    private void validateDefaultBreed(Breed breed) {
        if (breed.isSpeciesDefault()) {
            checkIfDefaultExists(breed.getSpecies());
        }
    }

    /**
     * Creates and saves a default breed for the specified species.
     * <p>
     * This method ensures that a default breed does not already exist for the given species.
     * If none exists, it creates a new default breed, sets its name, species, and marks it as default,
     * and then saves it to the repository.
     * </p>
     *
     * @param species the species for which to create the default breed
     * @return the newly created default breed
     * @throws NullPointerException if the species is null
     * @throws DomainException if a default breed already exists for the species
     */
    private Breed createDefaultBreedForSpecies(Species species) {
        Objects.requireNonNull(species, "Species cannot be null");

        // Check if a default breed already exists for the species
        checkIfDefaultExists(species);

        // Create a new default breed for the species
        Breed defaultBreed = new Breed();
        defaultBreed.setName(species.getDefaultBreedName());
        defaultBreed.setSpecies(species);
        defaultBreed.setSpeciesDefault(true);

        // Save the new default breed to the repository
        return breedRepository.save(defaultBreed);
    }

    /**
     * Checks if a default breed already exists for the given species.
     * <p>
     * If a default breed is found, a {@link DomainException} is thrown to
     * prevent the creation of duplicate default breeds for the species.
     * </p>
     *
     * @param species the species for which to check the default breed existence
     * @throws DomainException if a default breed already exists for the species
     */
    private void checkIfDefaultExists(Species species) {
        // Query the repository to find a default breed for the specified species
        breedRepository.findBySpeciesAndIsSpeciesDefaultTrue(species)
            .ifPresent(existingDefault -> {
                // Throw an exception if a default breed is found
                throw DomainException.withCustomMessage(DUPLICATED_DATA,
                        String.format("A default breed already exists for species %s: %s",
                                species,
                                existingDefault.getName()));
            });
    }
}
