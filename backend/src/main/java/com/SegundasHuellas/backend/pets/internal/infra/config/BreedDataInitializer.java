package com.SegundasHuellas.backend.pets.internal.infra.config;

import com.SegundasHuellas.backend.pets.internal.application.service.BreedService;
import com.SegundasHuellas.backend.pets.internal.domain.entity.Breed;
import com.SegundasHuellas.backend.pets.internal.domain.enums.Species;
import com.SegundasHuellas.backend.pets.internal.infra.persistence.BreedRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Initializes default breed data for the application.
 * <p>
 * This component listens for the {@link ApplicationStartedEvent} and populates the database
 * with default breeds for dogs and cats if no breeds exist in the repository.
 * </p>
 * <p>
 * The default data is added in a transactional context and logs execution details
 * for monitoring purposes.
 * </p>
 *
 * <b>Features:</b>
 * - Load initial breed data during application startup.
 * - Prevent duplicate data if breeds are already present.
 * - Detailed logging of execution time and errors.
 *
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class BreedDataInitializer {

    private final BreedRepository breedRepository;
    private final BreedService breedService;

    /**
     * Handles the {@link ApplicationStartedEvent} to initiate the breed data loading process.
     * <p>
     * This method checks if the breed data already exists in the database. If not, it triggers
     * the loading process. It logs the start and end of the process, capturing execution time.
     * In case of an error, it logs the error and throws a RuntimeException.
     * </p>
     *
     * @param event the application started event.
     */
    @EventListener
    @Transactional
    @Order(1)
    public void handleApplicationStartedEvent(ApplicationStartedEvent event) {
        // Check if breeds already exist in the repository.
        if (breedRepository.count() > 0) {
            // Skip initialization if data already present.
            return;
        }

        try {
            // Log the start of the breed loading process.
            log.info("🐱🐶 Loading initial breeds...");
            long start = System.currentTimeMillis();

            // Load the initial breeds into the repository.
            loadInitialBreeds();

            // Log the execution time of the loading process.
            logExecutionTime(start);
        } catch (Exception e) {
            // Error message to log in case of failure.
            String msg = "❌ Error loading initial breeds: ";

            // Log the error along with the exception stack trace.
            log.error(msg, e);

            // Rethrow as a runtime exception for global handling.
            throw new RuntimeException(msg, e);
        }
    }

    /**
     * Logs the execution time of a process.
     *
     * <p>
     * This method calculates the duration of a process by subtracting the start
     * time from the current time, converting it to seconds, and logs the result.
     * </p>
     *
     * @param start the start time in milliseconds
     */
    private void logExecutionTime(long start) {
        // Calculate the execution time in seconds.
        double executionTime = (System.currentTimeMillis() - start) / 1000.0;

        // Log the execution time with a descriptive message.
        log.info("🐱🐶 Initial breeds loaded in {} seconds 🐱🐶", executionTime);
    }

    /**
     * Loads the initial breeds for the application.
     * <p>
     * This method populates the database with a predefined set of breeds for
     * dogs and cats.
     * </p>
     */
    private void loadInitialBreeds() {
        List<Breed> breeds = new ArrayList<>();

        // Dogs
        breeds.addAll(List.of(
                Breed.builder().name("Mestizo").species(Species.DOG).isSpeciesDefault(false).build(),
                Breed.builder().name("Labrador Retriever").species(Species.DOG).isSpeciesDefault(false).build(),
                Breed.builder().name("Pastor Alemán").species(Species.DOG).isSpeciesDefault(false).build(),
                Breed.builder().name("Bulldog Francés").species(Species.DOG).isSpeciesDefault(false).build(),
                Breed.builder().name("Yorkshire Terrier").species(Species.DOG).isSpeciesDefault(false).build(),
                Breed.builder().name("Golden Retriever").species(Species.DOG).isSpeciesDefault(false).build(),
                Breed.builder().name("Chihuahua").species(Species.DOG).isSpeciesDefault(false).build(),
                Breed.builder().name("Poodle").species(Species.DOG).isSpeciesDefault(false).build(),
                Breed.builder().name("Schnauzer Miniatura").species(Species.DOG).isSpeciesDefault(false).build(),
                Breed.builder().name("Rottweiler").species(Species.DOG).isSpeciesDefault(false).build(),
                Breed.builder().name("Beagle").species(Species.DOG).isSpeciesDefault(false).build(),
                Breed.builder().name("Boxer").species(Species.DOG).isSpeciesDefault(false).build(),
                Breed.builder().name("Husky Siberiano").species(Species.DOG).isSpeciesDefault(false).build(),
                Breed.builder().name("Pug").species(Species.DOG).isSpeciesDefault(false).build(),
                Breed.builder().name("Dálmata").species(Species.DOG).isSpeciesDefault(false).build(),
                Breed.builder().name("Cocker Spaniel").species(Species.DOG).isSpeciesDefault(false).build()
        ));

        // Cats
        breeds.addAll(List.of(
                Breed.builder().name("Mestizo").species(Species.CAT).isSpeciesDefault(false).build(),
                Breed.builder().name("Común Doméstico").species(Species.CAT).isSpeciesDefault(false).build(),
                Breed.builder().name("Siamés").species(Species.CAT).isSpeciesDefault(false).build(),
                Breed.builder().name("Persa").species(Species.CAT).isSpeciesDefault(false).build(),
                Breed.builder().name("Maine Coon").species(Species.CAT).isSpeciesDefault(false).build(),
                Breed.builder().name("Bengalí").species(Species.CAT).isSpeciesDefault(false).build(),
                Breed.builder().name("Angora Turco").species(Species.CAT).isSpeciesDefault(false).build(),
                Breed.builder().name("Ragdoll").species(Species.CAT).isSpeciesDefault(false).build(),
                Breed.builder().name("Británico de Pelo Corto").species(Species.CAT).isSpeciesDefault(false).build(),
                Breed.builder().name("Sphynx").species(Species.CAT).isSpeciesDefault(false).build(),
                Breed.builder().name("Azul Ruso").species(Species.CAT).isSpeciesDefault(false).build(),
                Breed.builder().name("Común Europeo").species(Species.CAT).isSpeciesDefault(false).build(),
                Breed.builder().name("Himalayo").species(Species.CAT).isSpeciesDefault(false).build(),
                Breed.builder().name("Exótico de Pelo Corto").species(Species.CAT).isSpeciesDefault(false).build(),
                Breed.builder().name("Birmano").species(Species.CAT).isSpeciesDefault(false).build(),
                Breed.builder().name("Munchkin").species(Species.CAT).isSpeciesDefault(false).build(),
                Breed.builder().name("Bosque de Noruega").species(Species.CAT).isSpeciesDefault(false).build()
        ));

        // Save the breeds to the database.
        breedService.createManyBreeds(breeds);
    }
}


